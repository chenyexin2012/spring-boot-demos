package com.holmes.springboot.shardingsphere.service;

import com.holmes.springboot.shardingsphere.entity.Order;

import java.util.List;
import java.util.Map;

public interface OrderService {

    List<Map<String, Object>> selectUserOrderList(Long userId);

    List<Order> selectList();

    List<Order> selectByUserId(Long userId);

    int deleteById(Long id);

    int insert(Order order);

    Order selectById(Long id);

    int update(Order order);

    int clearTable();
}
