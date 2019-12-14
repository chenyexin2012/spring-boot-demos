package com.holmes.springboot.redis;

import com.holmes.springboot.redis.utils.RedisUtil;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;

@SpringBootTest
@RunWith(SpringRunner.class)
@Slf4j
public class RedisSpringBootApplicationTest {

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
