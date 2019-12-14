package com.holmes.springboot.shardingsphere.mapper;

import com.holmes.springboot.shardingsphere.entity.Order;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

@Mapper
public interface OrderMapper {

    List<Map<String, Object>> selectUserOrderList(@Param("userId") Long userId);

    List<Order> selectList();

    List<Order> selectByUserId(Long userId);

    int deleteByPrimaryKey(Long id);

    int insert(Order record);

    int insertSelective(Order record);

    Order selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(Order record);

    int updateByPrimaryKey(Order record);

    int clearTable();
}