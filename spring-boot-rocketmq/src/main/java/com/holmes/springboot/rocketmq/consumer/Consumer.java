package com.holmes.springboot.rocketmq.consumer;

import com.holmes.springboot.rocketmq.constant.RocketmqConstant;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.spring.annotation.ConsumeMode;
import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.core.RocketMQListener;
import org.springframework.stereotype.Component;

import java.util.concurrent.atomic.AtomicInteger;

@Component
@Slf4j
@RocketMQMessageListener(topic = RocketmqConstant.TEST_TOPIC, consumerGroup = "my-consumer-test-topic", consumeMode = ConsumeMode.CONCURRENTLY)
public class Consumer implements RocketMQListener<String> {

    private static AtomicInteger count = new AtomicInteger(0);

    @Override
    public void onMessage(String message) {

        log.info("Thread: {}, message: {}, count: {}", Thread.currentThread().getId(), message, count.incrementAndGet());
    }
}
