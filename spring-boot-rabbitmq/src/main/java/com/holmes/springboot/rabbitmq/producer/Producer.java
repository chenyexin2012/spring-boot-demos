package com.holmes.springboot.rabbitmq.producer;

import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class Producer {

    @Autowired
    public AmqpTemplate template;

    public void send() {
        template.convertAndSend("hello", "hello rabbit mq");
    }

    public void sendDirectMessage(String message) {
        // 将消息发送到指定的交换机上
        template.convertAndSend("directExchange", "directRoutingKey", message);
    }

    public void sendFanoutMessage(String message) {
        // 将消息发送到指定的交换机上
        template.convertAndSend("fanoutExchange", null, message);
    }

    public void sendTopicMessage(String message) {
        // 将消息发送到指定的交换机上
        template.convertAndSend("fanoutExchange", "topicRoutingKey", message);
    }

    public void sendHeadersMessage(String message) {
        // 将消息发送到指定的交换机上
        MessageProperties properties = new MessageProperties();
        properties.setHeader("name", "holmes");
        Message msg = new Message(message.getBytes(), properties);
        template.send("headersExchange", null, msg);
    }
}
