package com.holmes.springboot.shardingsphere.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class User {

    private Long id;

    private String username;

    private String password;

    private Integer gender;

    private String phone;

    private String address;

    private Date createTime;

    private Date modifyTime;
}