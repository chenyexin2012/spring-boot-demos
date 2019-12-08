package com.holmes.springboot.shiro.shardingsphere.service.impl;

import com.holmes.springboot.shardingsphere.entity.User;
import com.holmes.springboot.shardingsphere.mapper.UserMapper;
import com.holmes.springboot.shardingsphere.service.UserService;
import com.holmes.springboot.shiro.shardingsphere.entity.User;
import com.holmes.springboot.shiro.shardingsphere.service.UserService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    @Resource
    private UserMapper userMapper;

    @Override
    public List<User> selectList() {
        return userMapper.selectList();
    }

    @Override
    public int deleteById(Long id) {
        return userMapper.deleteByPrimaryKey(id);
    }

    @Override
    public int insert(User record) {
        return userMapper.insert(record);
    }

    @Override
    public User selectById(Long id) {
        return userMapper.selectByPrimaryKey(id);
    }

    @Override
    public int update(User record) {
        return userMapper.updateByPrimaryKey(record);
    }

    @Override
    public int clearTable() {
        return userMapper.clearTable();
    }
}
