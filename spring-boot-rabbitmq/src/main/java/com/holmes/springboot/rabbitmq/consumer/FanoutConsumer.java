package com.holmes.springboot.rabbitmq.consumer;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

/**
 * 监听队列 fanoutQueue1, fanoutQueue2
 */
@RabbitListener(queues = {"fanoutQueue1", "fanoutQueue2"})
@Component
@Slf4j
public class FanoutConsumer {

    @RabbitHandler
    public void handler(String message) {
        log.info("FanoutConsumer message: {}", message);
    }
}
