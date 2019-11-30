package com.holmes.springboot.activemq;

import com.holmes.springboot.activemq.producer.Producer;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.jms.annotation.EnableJms;

@SpringBootApplication
@EnableJms
public class ActivemqSpringBootApplication {

    public static void main(String[] args) {

        ApplicationContext context = SpringApplication.run(ActivemqSpringBootApplication.class, args);

        Producer producer = context.getBean(Producer.class);

        for (int i = 0; i < 100; i++) {
            producer.sendMessage("TEST.EXCLUSIVE.CONSUMER.TOPIC");
        }
    }
}
