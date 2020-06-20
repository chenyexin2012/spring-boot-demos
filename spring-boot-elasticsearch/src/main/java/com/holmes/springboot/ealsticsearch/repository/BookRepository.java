package com.holmes.springboot.ealsticsearch.repository;

import com.holmes.springboot.ealsticsearch.entity.Book;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

public interface BookRepository extends ElasticsearchRepository<Book, Long> {
}
