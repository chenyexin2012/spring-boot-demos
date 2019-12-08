package com.holmes.springboot.shiro.shardingsphere;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.holmes.springboot.shardingsphere.mapper")
public class ShardingSphereSpringBootApplication {

    public static void main(String[] args) {
        SpringApplication.run(ShardingSphereSpringBootApplication.class, args);
    }
}
