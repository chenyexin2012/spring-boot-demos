package com.holmes.springboot.kafka.producer;

import com.holmes.springboot.kafka.constant.KafkaConstant;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Component;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.util.concurrent.ListenableFutureCallback;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.atomic.AtomicInteger;

@Component
@Slf4j
public class Producer {

    private static AtomicInteger count = new AtomicInteger(0);

    @Autowired
    private KafkaTemplate kafkaTemplate;

    public void sendDefaultMessage(String message) {
        kafkaTemplate.sendDefault(message + "-" + count.incrementAndGet());
    }

    public void sendSyncMessage(String message) {

        ListenableFuture<SendResult<String, String>> future = kafkaTemplate.send(KafkaConstant.TEST_KAFKA_TOPIC,
                message + "-" + count.incrementAndGet());
        try {
            SendResult result = future.get();
            log.info("消息发送成功: " + result);
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
    }

    public void sendAsyncMessage(String message) {

        ListenableFuture<SendResult<String, String>> future = kafkaTemplate.send(KafkaConstant.TEST_KAFKA_TOPIC,
                message + "-" + count.incrementAndGet());
        future.addCallback(new ListenableFutureCallback<SendResult<String, String>>() {
            @Override
            public void onFailure(Throwable ex) {
                log.error("消息发送失败", ex);
            }

            @Override
            public void onSuccess(SendResult<String, String> result) {
                log.info("消息发送成功: " + result);
            }
        });
    }

    public void sendScheduledMessage(String topic, String message) {


    }
}
