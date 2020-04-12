package com.holmes.springboot.dubbo.service.api;

import com.alibaba.fastjson.JSONObject;
import com.holmes.springboot.dubbo.exception.BusinessException;
import com.holmes.springboot.dubbo.exception.NullInputException;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class IServiceMock implements IService {
    @Override
    public JSONObject handle(JSONObject input) throws NullInputException, BusinessException {
        log.info("接口处理失败，input={}", input);
        JSONObject error = new JSONObject();
        error.put("message", "接口处理失败");
        return error;
    }
}
