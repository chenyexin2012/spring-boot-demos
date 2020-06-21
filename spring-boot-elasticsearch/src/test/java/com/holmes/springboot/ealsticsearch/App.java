package com.holmes.springboot.ealsticsearch;

import com.holmes.springboot.ealsticsearch.entity.Book;
import com.holmes.springboot.ealsticsearch.repository.BookRepository;
import com.holmes.springboot.ealsticsearch.spider.BookPipeline;
import com.holmes.springboot.ealsticsearch.spider.JavaDoubaneProcessor;
import org.elasticsearch.index.query.*;
import org.elasticsearch.search.aggregations.AggregationBuilder;
import org.elasticsearch.search.aggregations.AggregationBuilders;
import org.elasticsearch.search.sort.FieldSortBuilder;
import org.elasticsearch.search.sort.SortBuilders;
import org.elasticsearch.search.sort.SortOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.data.elasticsearch.core.query.Query;
import org.springframework.test.context.junit4.SpringRunner;
import us.codecraft.webmagic.Spider;

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
    public void testSearch() {

    }

    @Test
    public void testTerm() {

        TermQueryBuilder termQueryBuilder = QueryBuilders.termQuery("name", "java");
        Iterator<Book> iterator = bookRepository.search(termQueryBuilder).iterator();
        while (iterator.hasNext()) {
            System.out.println(iterator.next());
        }
    }

    @Test
    public void test1() {

        /**
         * 查询豆瓣评分大于 9.5 的书籍
         */
        RangeQueryBuilder builder = QueryBuilders.rangeQuery("point").gt("9.5");
        Iterator<Book> iterator = bookRepository.search(builder).iterator();
        while (iterator.hasNext()) {
            System.out.println(iterator.next());
        }
    }

    @Test
    public void test2() {

        /**
         * 查询价格处于 90 - 100 的书籍
         */
        RangeQueryBuilder builder = QueryBuilders.rangeQuery("price").gte(90).lte(100);

        /**
         * 按评分从大到小排序，查询前10条数据
         */
        Pageable pageable = PageRequest.of(0, 10, Sort.Direction.DESC, "point");

        Iterator<Book> iterator = bookRepository.search(builder, pageable).iterator();
        while (iterator.hasNext()) {
            System.out.println(iterator.next());
        }
    }

    @Test
    public void test3() {

        /**
         * 查询价格处于 90 - 100 的书籍
         */
        RangeQueryBuilder rangeQueryBuilder = QueryBuilders.rangeQuery("price").gte(90).lte(100);

        /**
         * 使用FieldSortBuilder排序
         */
        FieldSortBuilder fieldSortBuilder = SortBuilders.fieldSort("point").order(SortOrder.DESC);

        /**
         * 分页
         */
        Pageable pageable = PageRequest.of(0, 10);

        Query query = new NativeSearchQueryBuilder()
                .withQuery(rangeQueryBuilder)
                .withSort(fieldSortBuilder)
                .withPageable(pageable)
                .build();

        Iterator<Book> iterator = bookRepository.search(query).iterator();
        while (iterator.hasNext()) {
            System.out.println(iterator.next());
        }
    }

    @Test
    public void test4() {

        /**
         * 查询价格处于 90 - 100 的java相关的书籍
         */
        TermQueryBuilder termQueryBuilder = QueryBuilders.termQuery("name", "java");
        RangeQueryBuilder rangeQueryBuilder = QueryBuilders.rangeQuery("price").gte(90).lte(100);

        /**
         * 使用BoolQueryBuilder进行复合查询
         */
        BoolQueryBuilder query = QueryBuilders.boolQuery();
        query.must(termQueryBuilder).must(rangeQueryBuilder);

        Iterator<Book> iterator = bookRepository.search(query).iterator();
        while (iterator.hasNext()) {
            System.out.println(iterator.next());
        }
    }

    @Test
    public void test5() {

        /**
         * match在匹配时会对所查找的关键词进行分词，然后按分词匹配查找，而term会直接对关键词进行查找。
         */
        MatchQueryBuilder matchQueryBuilder = QueryBuilders.matchQuery("name", "Java并发编程实战");
        TermQueryBuilder termQueryBuilder = QueryBuilders.termQuery("name", "Java并发编程实战");

        Iterator<Book> iterator = bookRepository.search(matchQueryBuilder).iterator();
        while (iterator.hasNext()) {
            System.out.println(iterator.next());
        }
    }

    @Test
    public void test6() {

    }

    @Test
    public void test7() {

    }
}
