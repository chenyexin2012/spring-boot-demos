package com.holmes.springboot.multidatasource.service;


import com.holmes.springboot.multidatasource.entity.User;

import java.util.List;

public interface UserService {

    List<User> selectList(Integer startPage, Integer pageSize);

    int deleteById(Long id);

    int insert(User user);

    User selectById(Long id);

    int update(User user);
}
