package com.holmes.springboot.activemq.consumer;

import lombok.extern.slf4j.Slf4j;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

import javax.jms.JMSException;
import javax.jms.Session;
import javax.jms.TextMessage;
import java.util.Random;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

@Slf4j
@Component
public class AckConsumer {

    private static AtomicInteger count = new AtomicInteger(0);

    private Random random = new Random();

    @JmsListener(destination = "TEST.ACK.TOPIC", containerFactory = "jmsListenerContainerFactoryA")
    public void consumerA(TextMessage message, Session session) {
        try {
            TimeUnit.MILLISECONDS.sleep(random.nextInt(100));
            System.out.printf(Thread.currentThread().getName() + " receive message: {%s}{%d}\n",
                    message.getText(), count.getAndIncrement());

            // 模拟异常
            int i = 1 / 0;

            // 手动确认
            message.acknowledge();
        } catch (Exception e) {
            try {
                // 不使用recover则等到重启才能再次消费
                session.recover();
                //e.printStackTrace();
                log.error("consumerA exception");
            } catch (JMSException e1) {
                e1.printStackTrace();
            }
        }
    }
}
