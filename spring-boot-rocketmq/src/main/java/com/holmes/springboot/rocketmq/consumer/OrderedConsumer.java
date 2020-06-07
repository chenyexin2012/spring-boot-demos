package com.holmes.springboot.rocketmq.consumer;

import com.holmes.springboot.rocketmq.constant.RocketmqConstant;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.common.message.MessageExt;
import org.apache.rocketmq.spring.annotation.ConsumeMode;
import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.core.RocketMQListener;
import org.springframework.stereotype.Component;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * 顺序消费
 *
 * 通过打印 queueId 和 threadId 可以看到每个 queue 都有一个单独的 consumer 进行消费
 *
 * 生产者方法见 Producer.orderMessage
 */
@Component
@Slf4j
@RocketMQMessageListener(topic = RocketmqConstant.TEST_ORDER_TOPIC, consumerGroup = "my-consumer-test-order-topic", consumeMode = ConsumeMode.ORDERLY)
public class OrderedConsumer implements RocketMQListener<MessageExt> {

    private static AtomicInteger count = new AtomicInteger(0);

    @Override
    public void onMessage(MessageExt message) {

        log.info("Thread: {}, queue: {}, message: {}, count: {}", Thread.currentThread().getId(), message.getQueueId(),
                new String(message.getBody()), count.incrementAndGet());
    }
}
