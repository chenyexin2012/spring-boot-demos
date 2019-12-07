package com.holmes.springboot.shardingsphere.service.impl;

import com.holmes.springboot.shardingsphere.entity.Order;
import com.holmes.springboot.shardingsphere.mapper.OrderMapper;
import com.holmes.springboot.shardingsphere.service.OrderService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

@Service
public class OrderServiceImpl implements OrderService {

    @Resource
    private OrderMapper orderMapper;

    @Override
    public List<Map<String, Object>> selectUserOrderList(Long userId) {
        return orderMapper.selectUserOrderList(userId);
    }

    @Override
    public List<Order> selectList() {
        return orderMapper.selectList();
    }

    @Override
    public List<Order> selectByUserId(Long userId) {
        return orderMapper.selectByUserId(userId);
    }

    @Override
    public int deleteById(Long id) {
        return orderMapper.deleteByPrimaryKey(id);
    }

    @Override
    public int insert(Order order) {
        return orderMapper.insert(order);
    }

    @Override
    public Order selectById(Long id) {
        return orderMapper.selectByPrimaryKey(id);
    }

    @Override
    public int update(Order order) {
        return orderMapper.updateByPrimaryKey(order);
    }

    @Override
    public int clearTable() {
        return orderMapper.clearTable();
    }
}
