package com.holmes.springboot.shiro.mybatis.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class User {

    private Long id;

    private String userName;

    private String userPassword;

    private String userEmail;

    private String address;
}