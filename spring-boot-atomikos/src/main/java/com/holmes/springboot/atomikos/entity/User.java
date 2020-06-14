package com.holmes.springboot.atomikos.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class User extends BasePo {

    private Long id;

    private String userName;

    private String userPassword;

    private String userEmail;

    private String address;
}