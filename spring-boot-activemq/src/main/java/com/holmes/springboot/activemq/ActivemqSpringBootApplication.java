package com.holmes.springboot.activemq;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.jms.annotation.EnableJms;

@SpringBootApplication
@EnableJms
public class ActivemqSpringBootApplication {

    public static void main(String[] args) {

        SpringApplication.run(ActivemqSpringBootApplication.class, args);
    }
}
