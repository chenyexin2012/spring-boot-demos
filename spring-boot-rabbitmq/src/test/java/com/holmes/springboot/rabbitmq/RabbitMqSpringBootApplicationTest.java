package com.holmes.springboot.rabbitmq;

import com.holmes.springboot.rabbitmq.producer.Producer;
import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.concurrent.TimeUnit;

@RunWith(SpringRunner.class)
@SpringBootTest
public class RabbitMqSpringBootApplicationTest {

    @Autowired
    public Producer producer;

    @Test
    public void testSend() {
        producer.send();
    }

    @Test
    public void testDirectMessage() {

        for (int i = 0; i < 10; i++) {
            producer.sendDirectMessage("测试直连交换机，id = " + i);
        }
    }

    @Test
    public void testFanoutMessage() {

        for (int i = 0; i < 10; i++) {
            producer.sendFanoutMessage("测试扇形交换机，id = " + i);
        }
    }

    @Test
    public void testTopicMessage() {

        for (int i = 0; i < 10; i++) {
            producer.sendTopicMessage("测试主题交换机，id = " + i);
        }
    }

    @Test
    public void testHeadersMessage() {

        for (int i = 0; i < 10; i++) {
            producer.sendHeadersMessage("测试头交换机，id = " + i);
        }
    }

    @After
    public void after() throws InterruptedException {
        TimeUnit.SECONDS.sleep(10);
    }
}
