package com.holmes.springboot.redis;

import com.holmes.springboot.redis.utils.RedisUtil;
import jakarta.annotation.Resource;
import org.junit.jupiter.api.Test;
import org.springframework.data.redis.core.RedisTemplate;

import java.util.concurrent.TimeUnit;


public class RedisSpringBootApplicationTest extends BaseSpringBootApplicationTest {

    @Resource
    private RedisTemplate<String, Object> redisTemplate;

    @Resource
    private RedisUtil redisUtil;

    @Test
    public void testAddValue() throws InterruptedException {

        long start = System.currentTimeMillis();
        for (int i = 0; i < 100000; i++) {
            redisTemplate.opsForValue().set(System.currentTimeMillis() + "", System.currentTimeMillis() + "");
//            TimeUnit.SECONDS.sleep(1);
        }
        long end = System.currentTimeMillis();

        System.out.println(end - start);
    }
}
