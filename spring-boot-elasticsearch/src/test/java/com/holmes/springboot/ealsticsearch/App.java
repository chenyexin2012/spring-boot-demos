package com.holmes.springboot.ealsticsearch;

import com.holmes.springboot.ealsticsearch.entity.Book;
import com.holmes.springboot.ealsticsearch.repository.BookRepository;
import org.apache.lucene.search.TermQuery;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.index.query.TermQueryBuilder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Date;
import java.util.Iterator;
import java.util.Optional;

@SpringBootTest
@RunWith(SpringRunner.class)
public class App {

    @Autowired
    private BookRepository bookRepository;

    @Test
    public void testInsert() {

        Book book = new Book();
        book.setId(1L);
        book.setName("Elasticsearch入门到精通");
        book.setIsbn("922654654651661");
        book.setCount(100);
        book.setPrice(56.6F);
        book.setCreateTime(new Date());
        book.setModifyTime(new Date());

        bookRepository.save(book);
    }

    @Test
    public void testGet() {

        Optional<Book> optional = bookRepository.findById(1L);
        System.out.println(optional.get());
    }

    @Test
    public void testTerm() {

        TermQueryBuilder termQueryBuilder = QueryBuilders.termQuery("name", "精通");
        Iterator<Book> iterator = bookRepository.search(termQueryBuilder).iterator();
        while (iterator.hasNext()) {
            System.out.println(iterator.next());
        }
    }
}
