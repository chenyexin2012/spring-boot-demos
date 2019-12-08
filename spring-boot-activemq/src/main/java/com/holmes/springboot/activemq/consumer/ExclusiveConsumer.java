package com.holmes.springboot.activemq.consumer;

import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

import java.util.Random;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 使用独占消费者保证顺序消费
 */
@Component
public class ExclusiveConsumer {

    private static AtomicInteger count = new AtomicInteger(0);

    private Random random = new Random();

    @JmsListener(destination = "TEST.EXCLUSIVE.CONSUMER.TOPIC?consumer.exclusive=true")
    public void consumerA(String message) throws InterruptedException {

        TimeUnit.MILLISECONDS.sleep(random.nextInt(100));
        System.out.printf("consumerA receive message: {%s}{%d}\n", message, count.getAndIncrement());
    }

    @JmsListener(destination = "TEST.EXCLUSIVE.CONSUMER.TOPIC?consumer.exclusive=true")
    public void consumerB(String message) throws InterruptedException {
        TimeUnit.MILLISECONDS.sleep(random.nextInt(100));
        System.out.printf("consumerB receive message: {%s}{%d}\n", message, count.getAndIncrement());
    }

    @JmsListener(destination = "TEST.EXCLUSIVE.CONSUMER.TOPIC?consumer.exclusive=true")
    public void consumerC(String message) throws InterruptedException {
        TimeUnit.MILLISECONDS.sleep(random.nextInt(100));
        System.out.printf("consumerC receive message: {%s}{%d}\n", message, count.getAndIncrement());
    }
}
