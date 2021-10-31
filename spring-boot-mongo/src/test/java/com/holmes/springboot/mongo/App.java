package com.holmes.springboot.mongo;

import com.holmes.springboot.mongo.entity.Book;
import com.holmes.springboot.mongo.spider.BookPipeline;
import com.holmes.springboot.mongo.spider.JavaDoubanProcessor;
import com.sun.jmx.remote.internal.ClientListenerInfo;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.test.context.junit4.SpringRunner;
import us.codecraft.webmagic.Spider;

import java.util.List;

@SpringBootTest
@RunWith(SpringRunner.class)
public class App {

    @Autowired
    private JavaDoubanProcessor javaDoubanProcessor;

    @Autowired
    private BookPipeline bookPipeline;

    @Autowired
    private MongoTemplate mongoTemplate;

    /**
     * 启动爬虫
     */
    @Test
    public void spider() {

        new Spider(javaDoubanProcessor)
                .addUrl("https://book.douban.com/tag/%E8%AE%A1%E7%AE%97%E6%9C%BA")
                .addPipeline(bookPipeline)
                .thread(10)
                .run();
    }

    @Test
    public void testGet() {

        List<Book> list = mongoTemplate.findAll(Book.class);
        Assert.assertNotNull(list);
    }
}
