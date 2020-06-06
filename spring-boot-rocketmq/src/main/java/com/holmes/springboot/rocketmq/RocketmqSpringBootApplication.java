package com.holmes.springboot.rocketmq;

import com.holmes.springboot.rocketmq.producer.Producer;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

@SpringBootApplication
public class RocketmqSpringBootApplication {

    public static void main(String[] args) {
        ApplicationContext context = SpringApplication.run(RocketmqSpringBootApplication.class, args);

        Producer producer = context.getBean(Producer.class);

        for (int i = 0; i < 1000; i++) {
            producer.orderMessage();
//            producer.syncMessage();
//            producer.asyncMessage();
//            producer.oneWayMessage();
        }
    }
}
