package com.holmes.springboot.shiro.activemq.producer;

import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.concurrent.TimeUnit;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ActivemqSpringBootApplicationTest {

    @Autowired
    private Producer producer;

    @Autowired
    MessageGroupProducer messageGroupProducer;

    /**
     * 测试消息发送
     */
    @Test
    public void testSendMessage() {
        for (int i = 0; i < 10; i++) {
            producer.sendMessage();
        }
    }

    /**
     * 测试独占消费者
     */
    @Test
    public void testExclusiveConsumer() {

        for (int i = 0; i < 100; i++) {
            producer.sendMessage("TEST.EXCLUSIVE.CONSUMER.TOPIC");
        }
    }

    /**
     * 测试消息分组
     */
    @Test
    public void testMessageGroup() {
        for (int i = 0; i < 100; i++) {
            messageGroupProducer.sendMessageGroupA();
            messageGroupProducer.sendMessageGroupB();
        }
    }

    /**
     * 测试消费者手动确认机制
     */
    @Test
    public void testAckConsumer() {

        for (int i = 0; i < 10; i++) {
            producer.sendMessage("TEST.ACK.TOPIC");
        }
    }


    @After
    public void after() throws InterruptedException {
        // 休眠30s，让消息被消费完
        TimeUnit.SECONDS.sleep(300);
    }
}
