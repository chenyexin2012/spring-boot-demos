package com.holmes.springboot.rabbitmq.consumer;

import com.rabbitmq.client.Channel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import java.io.IOException;

/**
 * 监听队列 headersQueue1, headersQueue2
 */
@RabbitListener(queues = {"headersQueue1", "headersQueue2"})
@Component
@Slf4j
public class HeadersConsumer {

    @RabbitHandler
    public void handler(Object message, Channel channel) {
        try {
            if(message instanceof Message) {
                Message msg = (Message) message;
                long deliveryTag = msg.getMessageProperties().getDeliveryTag();
                if(Math.random() < 0.3) {
                    int i = 1 / 0;
                }
                log.info("HeadersConsumer message: {}", new String(msg.getBody()));
                channel.basicAck(deliveryTag,true);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
