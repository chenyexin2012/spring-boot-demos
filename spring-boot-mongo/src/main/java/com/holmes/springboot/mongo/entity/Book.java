package com.holmes.springboot.mongo.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.io.Serializable;
import java.util.Date;

@Getter
@Setter
@ToString
@Document("book")
public class Book implements Serializable {

    @Id
    private Long id;

    /**
     * 书籍名称
     */
    @Field("name")
    private String name;

    /**
     * ISBN码
     */
    @Field("isbn")
    private String isbn;

    /**
     * 书籍价格
     */
    @Field("price")
    private Float price;

    /**
     * 作者
     */
    @Field("author")
    private String author;

    /**
     * 出版社
     */
    @Field("publisher")
    private String publisher;

    /**
     * 出版时间
     */
    @Field("pub_date")
    private String pubDate;

    /**
     * 豆瓣评分
     */
    @Field("point")
    private Float point;

    /**
     * 评论数量
     */
    @Field("comment_count")
    private String commentCount;

    /**
     * 简介
     */
    @Field("summary")
    private String summary;

    /**
     * 封面图片路径
     */
    @Field("img")
    private String img;

    /**
     * 创建时间
     */
    @Field("create_time")
    private Date createTime;
}