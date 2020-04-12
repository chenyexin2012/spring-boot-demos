package com.holmes.springboot.dubbo.service.api;

import com.holmes.springboot.dubbo.entity.User;
import com.holmes.springboot.dubbo.exception.BusinessException;
import lombok.extern.slf4j.Slf4j;

import java.util.Collections;
import java.util.List;

@Slf4j
public class UserServiceMock implements UserService {

    @Override
    public List<User> getUserList() throws BusinessException {
        log.info("getUserList接口发生异常");
        return Collections.emptyList();
    }

    @Override
    public User selectUserById(Integer userId) throws BusinessException {
        log.info("selectUserById接口发生异常, userId={}", userId);
        return new User();
    }

    @Override
    public Boolean insert(User user) throws BusinessException {
        log.info("insert接口发生异常, user={}", user);
        return false;
    }

    @Override
    public Boolean deleteById(Integer userId) throws BusinessException {
        log.info("deleteById接口发生异常, userId={}", userId);
        return false;
    }
}
