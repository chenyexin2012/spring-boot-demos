package com.holmes.springboot.rabbitmq.consumer;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

/**
 * 监听队列 directQueue
 */
@RabbitListener(queues = "directQueue")
@Component
@Slf4j
public class DirectConsumer {

    @RabbitHandler
    public void handler(String message) {
        log.info("DirectConsumer message: {}", message);
    }
}
