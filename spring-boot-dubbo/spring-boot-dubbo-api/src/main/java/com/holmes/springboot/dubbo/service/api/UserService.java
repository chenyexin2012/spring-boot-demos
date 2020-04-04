package com.holmes.springboot.dubbo.service.api;

import com.holmes.springboot.dubbo.entity.User;
import com.holmes.springboot.dubbo.exception.BusinessException;

import java.util.List;

public interface UserService {

    List<User> getUserList() throws BusinessException;

    User selectUserById(Integer userId) throws BusinessException;

    Boolean insert(User user) throws BusinessException;

    Boolean deleteById(Integer userId) throws BusinessException;
}
