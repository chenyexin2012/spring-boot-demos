package com.holmes.springboot.cache;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching
public class CacheSpringBootApplication {
    public static void main(String[] args) {
        SpringApplication.run(CacheSpringBootApplication.class, args);
    }
}
