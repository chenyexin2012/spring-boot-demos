package com.holmes.springboot.shiro.shardingsphere.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Order {

    private Long id;

    private Long userId;

    private String orderSn;

    private Integer status;

    private Date createTime;

    private Date modifyTime;
}