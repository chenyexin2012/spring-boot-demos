package com.holmes.springboot.atomikos.service.impl;

import com.holmes.springboot.atomikos.entity.User;
import com.holmes.springboot.atomikos.mapper.UserMapper;
import com.holmes.springboot.atomikos.service.UserService;
import com.holmes.springboot.atomikos.utils.PageUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.transaction.Transactional;
import java.util.List;

@Service
@Slf4j
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

    @Transactional
    @Override
    public int insert(User record) {

        record.setDataSourceFlag("db1");
        userMapper.insert(record);

//        // 模拟异常
//        if(Math.random() < 0.2) {
//            log.info("发生了异常：1");
//            int i = 1 / 0;
//        }

        record.setDataSourceFlag("db2");
        userMapper.insert(record);

//        // 模拟异常
//        if(Math.random() < 0.2) {
//            log.info("发生了异常：2");
//            int i = 1 / 0;
//        }

        return 1;
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
