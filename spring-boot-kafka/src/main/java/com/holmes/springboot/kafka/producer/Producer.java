package com.holmes.springboot.kafka.producer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
public class Producer {

    @Autowired
    private KafkaTemplate kafkaTemplate;

    public void sendDefaultMessage(String topic, String message) {
        kafkaTemplate.send(topic, message);
    }
}
