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

@Document(indexName = "douban_book_index", type = "douban_book", shards = 3, replicas = 2)
@Getter
@Setter
@ToString
public class Book implements Serializable {

    @Id
    private Long id;

    /**
     * 书籍名称
     */
    @Field(type = FieldType.Text, analyzer = "ik_max_word", searchAnalyzer = "ik_max_word")
    private String name;

    /**
     * ISBN码
     */
    private String isbn;

    /**
     * 书籍价格
     */
    private Float price;


    /**
     * 作者
     */
    private String author;

    /**
     * 出版社
     */
    @Field(type = FieldType.Text, analyzer = "ik_max_word", searchAnalyzer = "ik_max_word")
    private String publisher;

    /**
     * 出版时间
     */
    private String pubDate;

    /**
     * 豆瓣评分
     */
    private String point;

    /**
     * 评论数量
     */
    private String commentCount;

    /**
     * 简介
     */
    @Field(type = FieldType.Text, analyzer = "ik_max_word", searchAnalyzer = "ik_max_word")
    private String summary;

    /**
     * 封面图片路径
     */
    private String img;

    @Field(format = DateFormat.date_time, store = true, type = FieldType.Date)
    private Date createTime;
}
