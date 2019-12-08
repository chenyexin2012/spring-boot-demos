package com.holmes.springboot.shiro.mybatis.service;

import com.holmes.springboot.mybatis.entity.User;
import com.holmes.springboot.shiro.mybatis.entity.User;

import java.util.List;

public interface UserService {

    List<User> selectList(Integer startPage, Integer pageSize);

    int deleteById(Long id);

    int insert(User user);

    User selectById(Long id);

    int update(User user);
}
