package com.holmes.springboot.shiro.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Permission implements Serializable {

    private Integer id;

    private String permissionUrl;
}
