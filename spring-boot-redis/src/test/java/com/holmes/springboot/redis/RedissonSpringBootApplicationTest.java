package com.holmes.springboot.redis;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

@Slf4j
public class RedissonSpringBootApplicationTest extends BaseSpringBootApplicationTest {

    @Autowired
    private RedissonClient redissonClient;

    @Test
    public void testList() {

        List<String> list = redissonClient.getList("list");
        list.add(String.valueOf(System.nanoTime()));
        list.add(String.valueOf(System.nanoTime()));
        list.add(String.valueOf(System.nanoTime()));
        list.add(String.valueOf(System.nanoTime()));
        list.add(String.valueOf(System.nanoTime()));
        list.add(String.valueOf(System.nanoTime()));

        log.info(Arrays.toString(list.toArray()));
    }

    @Test
    public void testMap() {

        Map<String, String> map = redissonClient.getMap("map");
        map.put(String.valueOf(System.nanoTime()), String.valueOf(System.nanoTime()));
        map.put(String.valueOf(System.nanoTime()), String.valueOf(System.nanoTime()));
        map.put(String.valueOf(System.nanoTime()), String.valueOf(System.nanoTime()));
        map.put(String.valueOf(System.nanoTime()), String.valueOf(System.nanoTime()));
        map.put(String.valueOf(System.nanoTime()), String.valueOf(System.nanoTime()));

        for (Map.Entry<String, String> entry : map.entrySet()) {
            log.info("key: {}, value: {}", entry.getKey(), entry.getValue());
        }
    }
}
