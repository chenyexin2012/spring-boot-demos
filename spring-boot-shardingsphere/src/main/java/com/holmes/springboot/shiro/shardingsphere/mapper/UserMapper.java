package com.holmes.springboot.shiro.shardingsphere.mapper;

import com.holmes.springboot.shardingsphere.entity.User;
import com.holmes.springboot.shiro.shardingsphere.entity.User;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface UserMapper {

    List<User> selectList();

    int deleteByPrimaryKey(Long id);

    int insert(User record);

    int insertSelective(User record);

    User selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(User record);

    int updateByPrimaryKey(User record);

    int clearTable();
}