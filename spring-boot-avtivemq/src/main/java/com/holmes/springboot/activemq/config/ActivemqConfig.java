package com.holmes.springboot.activemq.config;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jms.core.JmsMessagingTemplate;

//@Configuration
public class ActivemqConfig {

    @Value("spring.activemq.broker-url")
    private String brokerUrl;

    @Value("spring.activemq.user")
    private String username;

    @Value("spring.activemq.password")
    private String password;

    @Bean
    public ActiveMQConnectionFactory connectionFactory() {

        ActiveMQConnectionFactory activeMQConnectionFactory = new ActiveMQConnectionFactory();
        activeMQConnectionFactory.setBrokerURL(brokerUrl);
        activeMQConnectionFactory.setUserName(username);
        activeMQConnectionFactory.setPassword(password);
        return activeMQConnectionFactory;
    }
    @Bean
    public JmsMessagingTemplate jmsMessagingTemplate(ActiveMQConnectionFactory connectionFactory) {
        return new JmsMessagingTemplate(connectionFactory);
    }
}
