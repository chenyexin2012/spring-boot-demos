package com.holmes.springboot.activemq.producer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsMessagingTemplate;
import org.springframework.stereotype.Component;

import java.util.concurrent.atomic.AtomicInteger;

@Component
public class Producer {

    private final static String TOPIC = "TEST.TOPIC";

    private static AtomicInteger count = new AtomicInteger(0);

    @Autowired
    private JmsMessagingTemplate jmsMessagingTemplate;

    public void sendMessage() {
        jmsMessagingTemplate.convertAndSend(TOPIC, String.format("Test Spring Boot Active Mq{%d}",
                count.getAndIncrement()));
    }

    public void sendMessage(String destinationName) {
        jmsMessagingTemplate.convertAndSend(destinationName, String.format("Test Spring Boot Active Mq{%d}",
                count.getAndIncrement()));
    }
}
