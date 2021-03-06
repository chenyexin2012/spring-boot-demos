package com.holmes.springboot.mybatisplus.mapper;

import com.holmes.springboot.mybatisplus.entity.Order;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author holmes
 * @since 2019-12-15
 */
@Mapper
public interface OrderMapper extends BaseMapper<Order> {

}
