package com.holmes.springboot.redis;

import com.holmes.springboot.redis.utils.RedisUtil;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;

import javax.annotation.Resource;


public class RedisSpringBootApplicationTest extends RedisSpringBootApplication {

    @Resource
    private RedisTemplate<String, Object> redisTemplate;

    @Autowired
    private RedisUtil redisUtil;

    @Test
    public void testAddValue() {

        long start = System.currentTimeMillis();
        for (int i = 0; i < 100000; i++) {
            redisTemplate.opsForValue().set(System.currentTimeMillis() + "", System.currentTimeMillis() + "");
        }
        long end = System.currentTimeMillis();

        System.out.println(end - start);
    }
}
