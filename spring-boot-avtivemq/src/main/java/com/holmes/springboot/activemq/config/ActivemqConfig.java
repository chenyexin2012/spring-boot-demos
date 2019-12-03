package com.holmes.springboot.activemq.config;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.activemq.RedeliveryPolicy;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jms.config.DefaultJmsListenerContainerFactory;

@Configuration
public class ActivemqConfig {

    @Value("${spring.activemq.broker-url}")
    private String brokerUrl;

    @Value("${spring.activemq.user}")
    private String username;

    @Value("${spring.activemq.password}")
    private String password;

    @Bean
    public RedeliveryPolicy redeliveryPolicy() {
        RedeliveryPolicy redeliveryPolicy = new RedeliveryPolicy();
        // 最大重传次数，默认为6
        redeliveryPolicy.setMaximumRedeliveries(10);
        // 初始重发延迟时间
        redeliveryPolicy.setInitialRedeliveryDelay(1000L);
        // 重发延迟时间
        redeliveryPolicy.setRedeliveryDelay(1000L);
        // 最大传送延迟，只在useExponentialBackOff为true时有效，
        // 假设首次重连间隔为10ms，倍数为2，那么第二次重连时间间隔为 20ms，
        // 第三次重连时间间隔为40ms，当重连时间间隔大的最大重连时间间隔时，
        // 以后每次重连时间间隔都为最大重连时间间隔。
        redeliveryPolicy.setMaximumRedeliveryDelay(-1L);
        // 是否在每次尝试发送失败后，按指数延长延迟时间
        redeliveryPolicy.setUseExponentialBackOff(false);
        // 第一次失败后重新发送之前等待500毫秒,第二次失败再等待500 * 2毫秒,这里的2就是value
        redeliveryPolicy.setBackOffMultiplier(2.0);
        // 是否避免消息碰撞
        redeliveryPolicy.setUseCollisionAvoidance(true);

        return redeliveryPolicy;
    }

    @Bean
    public ActiveMQConnectionFactory activeMQConnectionFactory() {

        ActiveMQConnectionFactory activeMQConnectionFactory = new ActiveMQConnectionFactory();
        activeMQConnectionFactory.setBrokerURL(brokerUrl);
        activeMQConnectionFactory.setUserName(username);
        activeMQConnectionFactory.setPassword(password);
        activeMQConnectionFactory.setRedeliveryPolicy(redeliveryPolicy());
        return activeMQConnectionFactory;
    }

    @Bean
    public DefaultJmsListenerContainerFactory jmsListenerContainerFactoryA() {
        DefaultJmsListenerContainerFactory container = new DefaultJmsListenerContainerFactory();
        container.setConnectionFactory(activeMQConnectionFactory());
        // 关闭事务
        container.setSessionTransacted(false);
        // 单条消息确认
        container.setSessionAcknowledgeMode(4);
        // 设置消费者并发数量
        container.setConcurrency("1");
        return container;
    }
}
