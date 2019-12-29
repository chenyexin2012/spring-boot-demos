package com.holmes.springboot.kafka;

import com.holmes.springboot.kafka.constant.KafkaConstant;
import com.holmes.springboot.kafka.producer.Producer;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.concurrent.TimeUnit;

@RunWith(SpringRunner.class)
@SpringBootTest
public class KafkaSpringBootApplicationTest {

    @Autowired
    private Producer producer;

    @Test
    public void testSendMessage() throws InterruptedException {

        for (int i = 0; i < 10; i++) {
//            producer.sendDefaultMessage(KafkaConstant.DEFAULT_KAFKA_TOPIC, String.valueOf(System.nanoTime()));
            producer.sendDefaultMessage(KafkaConstant.BATCH_KAFKA_TOPIC, "春花秋月何时了，往事知多少！");
//            producer.sendDefaultMessage(KafkaConstant.ACK_KAFKA_TOPIC, "春花秋月何时了，往事知多少！");
        }
        TimeUnit.SECONDS.sleep(10);
    }


}
