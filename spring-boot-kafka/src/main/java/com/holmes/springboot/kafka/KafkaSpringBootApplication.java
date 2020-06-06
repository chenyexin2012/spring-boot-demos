package com.holmes.springboot.kafka;

import com.holmes.springboot.kafka.producer.Producer;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

@SpringBootApplication
public class KafkaSpringBootApplication {

    public static void main(String[] args) {
        ApplicationContext context = SpringApplication.run(KafkaSpringBootApplication.class, args);

        Producer producer = context.getBean(Producer.class);

        for (int i = 0; i < 10000000; i++) {
            producer.sendSyncMessage("测试同步发送消息");
//            producer.sendAsyncMessage("测试异步发送消息");
//            producer.sendDefaultMessage(String.valueOf(System.nanoTime()));
////            producer.sendDefaultMessage(KafkaConstant.BATCH_KAFKA_TOPIC, "春花秋月何时了，往事知多少！");
////            producer.sendDefaultMessage(KafkaConstant.ACK_KAFKA_TOPIC, "春花秋月何时了，往事知多少！");
        }
    }
}
