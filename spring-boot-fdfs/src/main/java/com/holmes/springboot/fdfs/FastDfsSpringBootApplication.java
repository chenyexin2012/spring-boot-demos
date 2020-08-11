package com.holmes.springboot.fdfs;

import com.holmes.springboot.fdfs.core.FastDFSClient;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class FastDfsSpringBootApplication {

    public static void main(String[] args) {

        SpringApplication.run(FastDfsSpringBootApplication.class, args).getBean(FastDFSClient.class);
    }
}
