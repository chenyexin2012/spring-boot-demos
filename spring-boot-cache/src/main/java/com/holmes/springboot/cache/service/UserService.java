package com.holmes.springboot.cache.service;

import com.holmes.springboot.cache.entity.User;

import java.util.List;

public interface UserService {

    List<User> selectList(Integer startPage, Integer pageSize);

    int deleteById(Long id);

    User insert(User user);

    User selectById(Long id);

    User update(User user);
}
