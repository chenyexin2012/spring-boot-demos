package com.holmes.springboot.activemq.config;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jms.config.DefaultJmsListenerContainerFactory;
import org.springframework.jms.core.JmsMessagingTemplate;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.listener.DefaultMessageListenerContainer;
import org.springframework.jms.listener.MessageListenerContainer;

import javax.jms.ConnectionFactory;

@Configuration
public class ActivemqConfig {

//    @Value("spring.activemq.broker-url")
//    private String brokerUrl;
//
//    @Value("spring.activemq.user")
//    private String username;
//
//    @Value("spring.activemq.password")
//    private String password;
//
//    @Bean
//    public ActiveMQConnectionFactory connectionFactory() {
//
//        ActiveMQConnectionFactory activeMQConnectionFactory = new ActiveMQConnectionFactory();
//        activeMQConnectionFactory.setBrokerURL(brokerUrl);
//        activeMQConnectionFactory.setUserName(username);
//        activeMQConnectionFactory.setPassword(password);
//        return activeMQConnectionFactory;
//    }

    @Bean
    public DefaultJmsListenerContainerFactory jmsListenerContainerFactoryA(ConnectionFactory connectionFactory) {
        DefaultJmsListenerContainerFactory container = new DefaultJmsListenerContainerFactory();
        container.setConnectionFactory(connectionFactory);
        // 关闭事务
        container.setSessionTransacted(false);
        // 单条消息确认
        container.setSessionAcknowledgeMode(4);
        return container;
    }
}
