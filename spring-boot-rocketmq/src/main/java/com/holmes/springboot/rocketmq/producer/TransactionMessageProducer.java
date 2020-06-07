package com.holmes.springboot.rocketmq.producer;

import com.holmes.springboot.rocketmq.constant.RocketmqConstant;
import org.apache.rocketmq.spring.core.RocketMQTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Component;

import java.util.concurrent.atomic.AtomicInteger;

@Component
public class TransactionMessageProducer {

    private static AtomicInteger count = new AtomicInteger(0);

    @Autowired
    private RocketMQTemplate rocketMQTemplate;

    /**
     * 发送事务消息
     */
    public void transactionMessage() {

        String msg = "TEST ROCKET MQ TRANSACTION MESSAGE, COUNT=" + count.incrementAndGet();
        Message<String> message = MessageBuilder.withPayload(msg).build();
        rocketMQTemplate.sendMessageInTransaction(RocketmqConstant.TEST_TRANSACTION_TOPIC, message, "arg");
    }
}
