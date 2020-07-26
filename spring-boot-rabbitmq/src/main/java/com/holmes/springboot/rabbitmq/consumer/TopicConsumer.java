package com.holmes.springboot.rabbitmq.consumer;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

/**
 * 监听队列 topicQueue1, topicQueue2
 */
@RabbitListener(queues = {"topicQueue1", "topicQueue2"})
@Component
@Slf4j
public class TopicConsumer {

    @RabbitHandler
    public void handler(String message) {
        log.info("TopicConsumer message: {}", message);
    }
}
