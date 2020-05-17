package com.holmes.springboot.cache.service.impl;

import com.holmes.springboot.cache.entity.User;
import com.holmes.springboot.cache.mapper.UserMapper;
import com.holmes.springboot.cache.service.UserService;
import com.holmes.springboot.cache.utils.PageUtils;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
@CacheConfig(cacheNames = {"user"})
public class UserServiceImpl implements UserService {

    @Resource
    private UserMapper userMapper;

//    @Cacheable(key = "#startPage + ':' + #pageSize")
//    @Cacheable(keyGenerator = "userKeyGenerator")
//    @Cacheable(key = "#root.target + '.' + #root.method.name")
    @Cacheable(key = "#root.targetClass.name + '.' + #root.methodName + ':' + #root.args[0] + ':' + #root.args[1]")
    @Override
    public List<User> selectList(Integer startPage, Integer pageSize) {
        return PageUtils.getPageList(startPage, pageSize, () -> userMapper.selectList());
    }

    @CacheEvict(allEntries = true)
    @Override
    public int deleteById(Long id) {
        return userMapper.deleteByPrimaryKey(id);
    }

    @CachePut(key = "#record.id")
    @Override
    public User insert(User record) {
        if(userMapper.insert(record) > 0) {
            return record;
        }
        return null;
    }

    @Cacheable(key = "#id")
    @Override
    public User selectById(Long id) {
        return userMapper.selectByPrimaryKey(id);
    }

    @CachePut(key = "#record.id")
    @Override
    public User update(User record) {
        if(userMapper.updateByPrimaryKey(record) > 0) {
            return record;
        }
        return null;
    }
}
