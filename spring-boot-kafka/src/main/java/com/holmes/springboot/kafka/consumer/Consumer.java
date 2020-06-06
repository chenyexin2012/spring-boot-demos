package com.holmes.springboot.kafka.consumer;

import com.holmes.springboot.kafka.constant.KafkaConstant;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

@Component
@Slf4j
public class Consumer {

    private static AtomicInteger count = new AtomicInteger(0);

    @KafkaListener(topics = {KafkaConstant.TEST_KAFKA_TOPIC}, containerFactory = "kafkaListenerContainerFactory")
    public void handleTestMessage(ConsumerRecord consumerRecord) {

        log.info("Thread: {}, partition: {}, key: {}, message: {}, count: {}", Thread.currentThread().getId(), consumerRecord.partition(),
                consumerRecord.key(), consumerRecord.value(), count.incrementAndGet());
//        log.info("handle message");
//        log.info("<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<");
//        log.info("key = {}", consumerRecord.key());
//        log.info("topic = {}", consumerRecord.topic());
//        log.info("partition = {}", consumerRecord.partition());
//        log.info("timestamp = {}", consumerRecord.timestamp());
//        log.info("message: {}", consumerRecord.value());
//        log.info(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
    }

    @KafkaListener(topics = {KafkaConstant.DEFAULT_KAFKA_TOPIC}, containerFactory = "kafkaListenerContainerFactory")
    public void handleDefaultMessage(ConsumerRecord consumerRecord) {

        log.info("partition: {}, key: {}, message: {}, count: {}", consumerRecord.partition(),
                consumerRecord.key(), consumerRecord.value(), count.incrementAndGet());
//        log.info("handle message");
//        log.info("<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<");
//        log.info("key = {}", consumerRecord.key());
//        log.info("topic = {}", consumerRecord.topic());
//        log.info("partition = {}", consumerRecord.partition());
//        log.info("timestamp = {}", consumerRecord.timestamp());
//        log.info("message: {}", consumerRecord.value());
//        log.info(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
    }

    @KafkaListener(topics = {KafkaConstant.BATCH_KAFKA_TOPIC}, containerFactory = "batchKafkaListenerContainerFactory")
    public void handleBatchMessage(List<ConsumerRecord> consumerRecords) {

        log.info("handle message");
        for (ConsumerRecord consumerRecord : consumerRecords) {
            log.info("<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<");
            log.info("key = {}", consumerRecord.key());
            log.info("topic = {}", consumerRecord.topic());
            log.info("partition = {}", consumerRecord.partition());
            log.info("timestamp = {}", consumerRecord.timestamp());
            log.info("message: {}", consumerRecord.value());
            log.info(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
        }
    }

    @KafkaListener(topics = KafkaConstant.ACK_KAFKA_TOPIC, containerFactory = "ackKafkaListenerContainerFactory")
    public void handleMessage(ConsumerRecord consumerRecord, Acknowledgment acknowledgment) {

        try {
            log.info("handle message");
            log.info("<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<");
            log.info("key = {}", consumerRecord.key());
            log.info("topic = {}", consumerRecord.topic());
            log.info("partition = {}", consumerRecord.partition());
            log.info("timestamp = {}", consumerRecord.timestamp());
            log.info("message: {}", consumerRecord.value());
            log.info(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
            // 手动提交
            acknowledgment.acknowledge();
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        } finally {
        }
    }

}
