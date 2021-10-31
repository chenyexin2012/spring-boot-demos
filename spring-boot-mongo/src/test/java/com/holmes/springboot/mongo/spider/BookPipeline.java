package com.holmes.springboot.mongo.spider;

import com.holmes.springboot.mongo.entity.Book;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Component;
import us.codecraft.webmagic.ResultItems;
import us.codecraft.webmagic.Task;
import us.codecraft.webmagic.pipeline.Pipeline;

import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

@Component
@Slf4j
public class BookPipeline implements Pipeline {

    private final static AtomicLong ID = new AtomicLong(0);

    @Autowired
    private MongoTemplate mongoTemplate;

    @Override
    public void process(ResultItems resultItems, Task task) {

        List<Book> books = resultItems.get("data");
        if (null == books) {
            return;
        }
        for (Book book : books) {
            book.setId(ID.incrementAndGet());
            log.info("保存书籍信息，{}", mongoTemplate.save(book));
        }
    }
}
