package com.holmes.springboot.kafka.config;

import com.holmes.springboot.kafka.constant.KafkaConstant;
import lombok.AllArgsConstructor;
import org.springframework.boot.autoconfigure.kafka.KafkaProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.*;
import org.springframework.kafka.listener.ContainerProperties;

@EnableConfigurationProperties(KafkaProperties.class)
@Configuration
@AllArgsConstructor
public class KafkaConfig {

    private KafkaProperties properties;

    @Bean
    public ProducerFactory<String, String> producerFactory() {

        ProducerFactory<String, String> producerFactory =
                new DefaultKafkaProducerFactory<>(properties.buildProducerProperties());
        return producerFactory;
    }

    @Bean
    public KafkaTemplate<String, String> kafkaTemplate() {

        KafkaTemplate<String, String> kafkaTemplate = new KafkaTemplate<>(producerFactory());
        return kafkaTemplate;
    }

    @Bean
    public ConsumerFactory<String, String> consumerFactory() {
        return new DefaultKafkaConsumerFactory<>(properties.buildConsumerProperties());
    }

    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, String> kafkaListenerContainerFactory() {
        ConcurrentKafkaListenerContainerFactory<String, String> factory =
                new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(consumerFactory());
        factory.setConcurrency(KafkaConstant.DEFAULT_NUMBER_OF_CONSUMERS);
        factory.setBatchListener(false);
        factory.getContainerProperties().setPollTimeout(3000);
        factory.setMissingTopicsFatal(false);
        return factory;
    }

    @Bean("batchKafkaListenerContainerFactory")
    public ConcurrentKafkaListenerContainerFactory<String, String> batchKafkaListenerContainerFactory() {
        ConcurrentKafkaListenerContainerFactory<String, String> factory =
                new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(consumerFactory());
        factory.setConcurrency(KafkaConstant.DEFAULT_NUMBER_OF_CONSUMERS);
        // 设置批量消费
        factory.setBatchListener(true);
        factory.getContainerProperties().setPollTimeout(3000);
        factory.setMissingTopicsFatal(false);
        return factory;
    }

    @Bean("ackKafkaListenerContainerFactory")
    public ConcurrentKafkaListenerContainerFactory<String, String> ackKafkaListenerContainerFactory() {
        ConcurrentKafkaListenerContainerFactory<String, String> factory =
                new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(consumerFactory());
        factory.getContainerProperties().setAckMode(ContainerProperties.AckMode.MANUAL_IMMEDIATE);
        factory.setConcurrency(KafkaConstant.DEFAULT_NUMBER_OF_CONSUMERS);
        factory.setMissingTopicsFatal(false);
        return factory;
    }

}
