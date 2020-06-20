package com.holmes.springboot.ealsticsearch;

import com.holmes.springboot.ealsticsearch.entity.Book;
import com.holmes.springboot.ealsticsearch.repository.BookRepository;
import com.holmes.springboot.ealsticsearch.spider.BookPipeline;
import com.holmes.springboot.ealsticsearch.spider.JavaDoubaneProcessor;
import org.apache.lucene.search.TermQuery;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.index.query.TermQueryBuilder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import us.codecraft.webmagic.Spider;

import java.util.Date;
import java.util.Iterator;
import java.util.Optional;

@SpringBootTest
@RunWith(SpringRunner.class)
public class App {

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private JavaDoubaneProcessor javaDoubaneProcessor;

    @Autowired
    private BookPipeline bookPipeline;

    /**
     * 启动爬虫
     */
    @Test
    public void spider() {

        new Spider(javaDoubaneProcessor)
                .addUrl("https://book.douban.com/tag/%E8%AE%A1%E7%AE%97%E6%9C%BA")
                .addPipeline(bookPipeline)
                .thread(10)
                .run();
    }

    @Test
    public void testInsert() {
    }

    @Test
    public void testGet() {

        Optional<Book> optional = bookRepository.findById(1L);
        System.out.println(optional.get());
    }

    @Test
    public void testTerm() {

        TermQueryBuilder termQueryBuilder = QueryBuilders.termQuery("name", "java");
        Iterator<Book> iterator = bookRepository.search(termQueryBuilder).iterator();
        while (iterator.hasNext()) {
            System.out.println(iterator.next());
        }
    }
}
