package com.holmes.springboot.rocketmq.dto;

import lombok.*;

import java.io.Serializable;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class OrderMessage implements Serializable {

    private Integer orderId;

    private String message;
}
