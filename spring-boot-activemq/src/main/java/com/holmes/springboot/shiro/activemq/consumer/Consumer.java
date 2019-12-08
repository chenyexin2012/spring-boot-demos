package com.holmes.springboot.shiro.activemq.consumer;

import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

import java.util.Random;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;


@Component
public class Consumer {

    private static AtomicInteger count = new AtomicInteger(0);

    private Random random = new Random();

    @JmsListener(destination = "TEST.TOPIC")
    public void consumerA(String message) throws InterruptedException {
        TimeUnit.MILLISECONDS.sleep(random.nextInt(100));
        System.out.printf("consumerA receive message: {%s}{%d}\n", message, count.getAndIncrement());
    }

    @JmsListener(destination = "TEST.TOPIC")
    public void consumerB(String message) throws InterruptedException {
        TimeUnit.MILLISECONDS.sleep(random.nextInt(100));
        System.out.printf("consumerB receive message: {%s}{%d}\n", message, count.getAndIncrement());
    }

    @JmsListener(destination = "TEST.TOPIC")
    public void consumerC(String message) throws InterruptedException {
        TimeUnit.MILLISECONDS.sleep(random.nextInt(100));
        System.out.printf("consumerC receive message: {%s}{%d}\n", message, count.getAndIncrement());
    }
}
