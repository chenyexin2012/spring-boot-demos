package com.holmes.springboot.mybatisplus.service.impl;

import com.holmes.springboot.mybatisplus.entity.Order;
import com.holmes.springboot.mybatisplus.mapper.OrderMapper;
import com.holmes.springboot.mybatisplus.service.IOrderService;
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
public class OrderServiceImpl extends ServiceImpl<OrderMapper, Order> implements IOrderService {

}
