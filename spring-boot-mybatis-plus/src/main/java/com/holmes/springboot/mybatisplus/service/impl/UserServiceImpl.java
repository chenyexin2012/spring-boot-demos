package com.holmes.springboot.mybatisplus.service.impl;

import com.holmes.springboot.mybatisplus.entity.User;
import com.holmes.springboot.mybatisplus.mapper.UserMapper;
import com.holmes.springboot.mybatisplus.service.IUserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author holmes
 * @since 2019-12-15
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements IUserService {

}
