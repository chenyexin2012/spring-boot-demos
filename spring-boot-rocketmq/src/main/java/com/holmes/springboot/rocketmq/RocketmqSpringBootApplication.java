package com.holmes.springboot.rocketmq;

import com.holmes.springboot.rocketmq.producer.Producer;
import com.holmes.springboot.rocketmq.producer.TransactionMessageProducer;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

@SpringBootApplication
public class RocketmqSpringBootApplication {

    public static void main(String[] args) {
        ApplicationContext context = SpringApplication.run(RocketmqSpringBootApplication.class, args);

        Producer producer = context.getBean(Producer.class);
        TransactionMessageProducer transactionMessageProducer = context.getBean(TransactionMessageProducer.class);

        for (int i = 0; i < 1; i++) {
            transactionMessageProducer.transactionMessage();
//            producer.filterSQLMessage();
//            producer.filterTagMessage();
//            producer.batchMessage();
//            producer.scheduledMessage();
//            producer.orderMessage();
//            producer.syncMessage();
//            producer.asyncMessage();
//            producer.oneWayMessage();
        }
    }
}
