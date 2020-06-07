package com.holmes.springboot.rocketmq.consumer;

import com.holmes.springboot.rocketmq.constant.RocketmqConstant;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.common.message.MessageExt;
import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.core.RocketMQListener;
import org.springframework.stereotype.Component;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * 事务消息消费者
 */
@Component
@Slf4j
@RocketMQMessageListener(topic = RocketmqConstant.TEST_TRANSACTION_TOPIC, consumerGroup = "my-consumer-test-transaction-topic")
public class TransactionMessageConsumer implements RocketMQListener<MessageExt> {

    private static AtomicInteger count = new AtomicInteger(0);

    @Override
    public void onMessage(MessageExt message) {
        log.info("Thread: {}, message: {}, count: {}", Thread.currentThread().getId(), new String(message.getBody()),
                count.incrementAndGet());
    }
}
