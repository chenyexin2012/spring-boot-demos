package com.holmes.springboot.dubbo.service.api;

import com.alibaba.fastjson.JSONObject;
import com.holmes.springboot.dubbo.exception.BusinessException;
import com.holmes.springboot.dubbo.exception.NullInputException;

/**
 * dubbo服务统一接口
 * 入参与返回参数统一使用JSONObject
 */
public interface IService {

    JSONObject handle(JSONObject input) throws NullInputException, BusinessException;
}
