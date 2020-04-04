package com.holmes.springboot.dubbo.entity;

import lombok.*;

import java.io.Serializable;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Address implements Serializable {

    private String province;

    private String city;

    private String street;
}
