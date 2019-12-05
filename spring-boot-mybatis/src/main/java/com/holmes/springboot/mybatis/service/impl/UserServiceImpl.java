package com.holmes.springboot.mybatis.service.impl;

import com.holmes.springboot.mybatis.entity.User;
import com.holmes.springboot.mybatis.mapper.UserMapper;
import com.holmes.springboot.mybatis.service.UserService;
import com.holmes.springboot.mybatis.utils.PageUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.function.Supplier;

@Service
public class UserServiceImpl implements UserService {

    @Resource
    private UserMapper userMapper;

    @Override
    public List<User> selectList(Integer startPage, Integer pageSize) {
        return PageUtils.getPageList(startPage, pageSize, () -> userMapper.selectList());
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
}
