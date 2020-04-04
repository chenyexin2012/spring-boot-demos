package com.holmes.springboot.dubbo.entity;

import lombok.*;

import java.io.Serializable;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class User implements Serializable {

    private Integer id;

    private String name;

    private Integer age;

    private Address address;
}
