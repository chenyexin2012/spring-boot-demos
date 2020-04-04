package com.holmes.springboot.dubbo.serivce;

import com.alibaba.fastjson.JSONObject;
import com.holmes.springboot.dubbo.exception.BusinessException;
import com.holmes.springboot.dubbo.exception.NullInputException;
import com.holmes.springboot.dubbo.service.api.IService;
import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.config.annotation.Service;

@Service(group = "helloService", timeout = 3000, version = "1.0.0")
@Slf4j
public class HelloService implements IService {

    @Override
    public JSONObject handle(JSONObject input) throws NullInputException, BusinessException {
        if (input == null) throw new NullInputException();
        try {
            log.info("receive message: {}", input.toJSONString());
            JSONObject result = new JSONObject();
            result.put("message", "hello");
            return result;
        } catch (Exception e) {
            log.error("接口调用失败", e);
            throw new BusinessException("接口调用失败", e);
        }
    }
}
