package com.holmes.springboot.ealsticsearch.spider;

import com.holmes.springboot.ealsticsearch.entity.Book;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.processor.PageProcessor;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 爬取豆瓣计算机类书籍
 */
@Slf4j
@Component
public class JavaDoubaneProcessor implements PageProcessor {

    private Site site = Site.me().setRetryTimes(3).setSleepTime(100).setTimeOut(3000);

    @Override
    public void process(Page page) {
        // 封面url
        List<String> imgs = page.getHtml().xpath("div[@id='subject_list']/ul/li/div[@class='pic']/a/img/@src").all();
        // 书名
        List<String> bookNames = page.getHtml().xpath("div[@id='subject_list']/ul/li/div[@class='info']/h2/a/text()").all();
        // 评分
        List<String> points = page.getHtml().xpath("div[@id='subject_list']/ul/li/div[@class='info']/div[@class='star clearfix']/span[@class='rating_nums']/text()").all();
        // 评论数量
        List<String> commentCounts = page.getHtml().xpath("div[@id='subject_list']/ul/li/div[@class='info']/div[@class='star clearfix']/span[@class='pl']/text()").all();
        // 简介
        List<String> summarys = page.getHtml().xpath("div[@id='subject_list']/ul/li/div[@class='info']/p/text()").all();
        // 作者 出版社 出版时间 价格
        List<String> pubInfos = page.getHtml().xpath("div[@id='subject_list']/ul/li/div[@class='info']/div[@class='pub']/text()").all();

        // 数据合并处理
        List<Book> books = new ArrayList<>();
        for (int i = 0; i < imgs.size(); i++) {
            try {
                Book book = new Book();
                book.setImg(imgs.get(i));
                book.setName(bookNames.get(i).trim());
                if (points.size() == imgs.size()) {
                    book.setPoint(Float.parseFloat(getNumber(points.get(i))));
                }

                String countStr = commentCounts.get(i);
                book.setCommentCount(countStr.substring(countStr.indexOf("(") + 1, countStr.indexOf("人")));
                if (summarys.size() == imgs.size()) {
                    book.setSummary(summarys.get(i).trim());
                }

                String pubInfo = pubInfos.get(i);
                String[] arr = pubInfo.split("/");
                if (arr.length > 3) {
                    book.setPrice(Float.valueOf(getNumber(arr[arr.length - 1].trim())));
                    book.setPubDate(arr[arr.length - 2].trim());
                    book.setPublisher(arr[arr.length - 3].trim());

                    // 作者合并
                    String author = "";
                    for (int j = 0; j < arr.length - 4; j++) {
                        author = author + arr[j] + "/";
                    }
                    author = author + arr[arr.length - 3];
                    book.setAuthor(author.trim());
                } else {
                    book.setAuthor(pubInfo);
                }
                books.add(book);
            } catch (Exception e) {
                log.error("书籍解析发生了异常, name: {}", bookNames.get(i), e);
            }
        }

        String firstUrl = "https://book.douban.com/tag/%E8%AE%A1%E7%AE%97%E6%9C%BA";
        if (page.getUrl().get().equals(firstUrl)) {
            List<String> allPage = page.getHtml().xpath("div[@class='paginator']/a/text()").all();
            Integer lastPage = Integer.parseInt(allPage.get(allPage.size() - 1));
            List<String> targetUtl = new ArrayList<>();
            for (int i = 2; i <= lastPage + 1; i++) {
                String pageUrl = firstUrl + "?start=" + ((i - 1) * 20);
                System.out.println(pageUrl);
                targetUtl.add(pageUrl);
            }
            page.addTargetRequests(targetUtl);
        }
        page.putField("data", books);

    }

    @Override
    public Site getSite() {
        return site;
    }

    /**
     * 提取数字
     *
     * @param str
     * @return
     */
    public static String getNumber(String str) {
        Pattern p = Pattern.compile("(\\d+\\.\\d+)");
        Matcher m = p.matcher(str);
        if (m.find()) {
            str = m.group(1) == null ? "" : m.group(1);
        } else {
            p = Pattern.compile("(\\d+)");
            m = p.matcher(str);
            if (m.find()) {
                str = m.group(1) == null ? "" : m.group(1);
            } else {
                str = "";
            }
        }
        return str;
    }
}
