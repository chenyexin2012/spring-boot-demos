package com.holmes.springboot.dubbo;

import org.apache.dubbo.config.spring.context.annotation.DubboComponentScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@DubboComponentScan(basePackages = {"com.holmes.springboot.dubbo.serivce"})
public class DubboProviderSpringBootApplication {

    public static void main(String[] args) {
        SpringApplication.run(DubboProviderSpringBootApplication.class, args);
    }
}
