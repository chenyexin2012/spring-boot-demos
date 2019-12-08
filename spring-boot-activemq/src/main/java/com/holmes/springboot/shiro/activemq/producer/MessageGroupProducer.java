package com.holmes.springboot.shiro.activemq.producer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsMessagingTemplate;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 通过给Message对象设置JMSXGroupID属性保证具有相同组名的
 * 消息能够被同一个消费者消费，从而保证顺序消费
 *
 * 也可以通过设置JMSXGroupSeq来关闭分组
 */
@Component
public class MessageGroupProducer {

    private final static String TOPIC = "TEST.MESSAGE.GROUP.TOPIC";

    private static AtomicInteger countA = new AtomicInteger(0);

    private static AtomicInteger countB = new AtomicInteger(0);

    @Autowired
    private JmsMessagingTemplate jmsMessagingTemplate;

    public void sendMessageGroupA() {

        Map<String, Object> headers = new HashMap<>();
        headers.put("JMSXGroupID", "groupA");
//        headers.put("JMSXGroupSeq", "-1");
        jmsMessagingTemplate.convertAndSend(TOPIC, String.format("Test Spring Boot Active Mq Message Group{%s}{%d}", "groupA",
                countA.getAndIncrement()), headers);
    }

    public void sendMessageGroupB() {

        Map<String, Object> headers = new HashMap<>();
        headers.put("JMSXGroupID", "groupB");
//        headers.put("JMSXGroupSeq", "-1");
        jmsMessagingTemplate.convertAndSend(TOPIC, String.format("Test Spring Boot Active Mq Message Group{%s}{%d}", "groupB",
                countB.getAndIncrement()), headers);
    }
}