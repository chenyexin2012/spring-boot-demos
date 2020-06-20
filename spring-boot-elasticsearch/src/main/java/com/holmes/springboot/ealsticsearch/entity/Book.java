package com.holmes.springboot.ealsticsearch.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.DateFormat;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import java.io.Serializable;
import java.util.Date;

@Document(indexName = "book_index", type = "book", shards = 3, replicas = 2)
@Getter
@Setter
@ToString
public class Book implements Serializable {

    @Id
    private Long id;

    @Field(type = FieldType.Text, analyzer = "ik_max_word", searchAnalyzer = "ik_max_word")
    private String name;

    private String isbn;

    private Float price;

    private Integer count;

    @Field(format = DateFormat.date_time, store = true, type = FieldType.Date)
    private Date createTime;

    @Field(format = DateFormat.date_time, store = true, type = FieldType.Date)
    private Date modifyTime;
}
