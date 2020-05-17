package com.holmes.springboot.cache.utils;

import org.springframework.cache.interceptor.KeyGenerator;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;

@Component
public class UserKeyGenerator implements KeyGenerator {

    @Override
    public Object generate(Object target, Method method, Object... params) {
        StringBuilder key = new StringBuilder(target.getClass().getName());
        key.append('.');
        key.append(method.getName());
        if(params.length > 0) {
            key.append(":");
            int i = 0;
            for (; i < params.length - 1; i++) {
                key.append(params[i]).append(':');
            }
            key.append(params[i]);
        }

        return key.toString();
    }
}
