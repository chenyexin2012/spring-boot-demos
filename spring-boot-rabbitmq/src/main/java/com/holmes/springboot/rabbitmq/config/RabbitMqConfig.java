package com.holmes.springboot.rabbitmq.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 在RabbitMQ中，生产者会将消息发送到交换机上，然后根据交换机的类型和配置来处理相应的消息，一般处理方式如下：
 *
 * 1. 将消息发送到某一个队列
 * 2. 将消息发送到多个队列
 * 3. 直接丢弃消息
 *
 * 交换机的种类有以下四种：
 *
 * 1. DirectExchange：直连交换机，根据消息携带的路由键（routing key）将消息传递给对应的队列。
 * 2. FanoutExchange：扇形交换机，将消息发送给绑定到自己身上的所有队列，此时路由键不起作用。
 * 3. TopicExchange：主题交换机，队列通过路由绑定到交换机上，然会交换机会根据消息的路由，将消息发送到一个或多个绑定队列上。
 * 4. HeadersExchange：头交换机，与主题交换机类似，但是它是通过多个消息的属性来将消息路由到指定的队列上。
 */
@Configuration
@Slf4j
public class RabbitMqConfig {

    @Bean
    public RabbitTemplate rabbitTemplate(ConnectionFactory connectionFactory) {
        RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
        // 设置开启Mandatory, 才能触发回调函数, 无论消息推送结果怎么样都强制调用回调函数
        rabbitTemplate.setMandatory(true);


        /**
         * 1. 推动到服务端，但是找不到对应的交换机，输出样例如下：
         *
         *      ConfirmCallback: correlationData：null
         *      ConfirmCallback: ack：false
         *      ConfirmCallback: cause：channel error; protocol method: #method<channel.close>(reply-code=404, reply-text=NOT_FOUND - no exchange 'directExchange1' in vhost '/', class-id=60, method-id=40)
         *
         * 2. 推动到服务端，找到对应的交换机，但是无法路由到指定队列，输出样例如下
         *
         *      ReturnCallback: message：(Body:'测试直连交换机，id = 0' MessageProperties [headers={}, contentType=text/plain, contentEncoding=UTF-8, contentLength=0, receivedDeliveryMode=PERSISTENT, priority=0, deliveryTag=0])
         *      ReturnCallback: replyCode：312
         *      ReturnCallback: replyText：NO_ROUTE
         *      ReturnCallback: exchange：directExchange
         *      ReturnCallback: routingKey：directRoutingKey1
         *
         *      ConfirmCallback: correlationData：null
         *      ConfirmCallback: ack：true
         *      ConfirmCallback: cause：null
         *
         * 3. 消息推送成功
         *
         *      ConfirmCallback: correlationData：null
         *      ConfirmCallback: ack：true
         *      ConfirmCallback: cause：null
         *
         */
        rabbitTemplate.setConfirmCallback(new RabbitTemplate.ConfirmCallback() {
            @Override
            public void confirm(CorrelationData correlationData, boolean ack, String cause) {
                log.info("ConfirmCallback: correlationData：{}", correlationData);
                log.info("ConfirmCallback: ack：{}", ack);
                log.info("ConfirmCallback: cause：{}", cause);
            }
        });

        rabbitTemplate.setReturnCallback(new RabbitTemplate.ReturnCallback() {
            @Override
            public void returnedMessage(Message message, int replyCode, String replyText, String exchange, String routingKey) {
                log.info("ReturnCallback: message：{}", message);
                log.info("ReturnCallback: replyCode：{}", replyCode);
                log.info("ReturnCallback: replyText：{}", replyText);
                log.info("ReturnCallback: exchange：{}", exchange);
                log.info("ReturnCallback: routingKey：{}", routingKey);
            }
        });
        return rabbitTemplate;
    }

    @Bean
    public Queue hello() {
        return new Queue("hello");
    }

    @Bean
    public Queue directQueue() {
        return new Queue("directQueue", true);
    }

    /**
     * 将队列通过路由键绑定到指定的交换机上
     *
     * @return
     */
    @Bean
    public Binding bindingDirect() {
        return BindingBuilder.bind(directQueue()).to(directExchange()).with("directRoutingKey");
    }

    @Bean
    public Queue fanoutQueue1() {
        return new Queue("fanoutQueue1", true);
    }

    @Bean
    public Queue fanoutQueue2() {
        return new Queue("fanoutQueue2", true);
    }

    /**
     * 将 fanoutQueue1， fanoutQueue2 绑定到同一个扇形交换机上
     * @return
     */
    @Bean
    public Binding bindingFanout1() {
        return BindingBuilder.bind(fanoutQueue1()).to(fanoutExchange());
    }

    @Bean
    public Binding bindingFanout2() {
        return BindingBuilder.bind(fanoutQueue2()).to(fanoutExchange());
    }

    @Bean
    public Queue topicQueue1() {
        return new Queue("topicQueue1", true);
    }

    @Bean
    public Queue topicQueue2() {
        return new Queue("topicQueue2", true);
    }

    /**
     * 将 topicQueue1， topicQueue2 绑定到同一个主题交换机上
     * @return
     */
    @Bean
    public Binding bindingTopic1() {
        return BindingBuilder.bind(topicQueue1()).to(topicExchange()).with("topicRoutingKey");
    }

    @Bean
    public Binding bindingTopic2() {
        return BindingBuilder.bind(topicQueue2()).to(topicExchange()).with("topicRoutingKey");
    }


    @Bean
    public Queue headersQueue1() {
        return new Queue("headersQueue1", true);
    }

    @Bean
    public Queue headersQueue2() {
        return new Queue("headersQueue2", true);
    }

    /**
     * 将 headersQueue1， headersQueue2 绑定到同一个头交换机上
     * @return
     */
    @Bean
    public Binding bindingHeaders1() {
        return BindingBuilder.bind(headersQueue1()).to(headersExchange()).where("name").matches("holmes");
    }

    @Bean
    public Binding bindingHeaders2() {
        return BindingBuilder.bind(headersQueue2()).to(headersExchange()).where("name").matches("holmes");
    }


    /**
     * 直连交换机
     *
     * @return
     */
    @Bean
    public DirectExchange directExchange() {
        return new DirectExchange("directExchange", true, false);
    }

    /**
     * 扇形交换机
     *
     * @return
     */
    @Bean
    public FanoutExchange fanoutExchange() {
        return new FanoutExchange("fanoutExchange", true, false);
    }



    /**
     * 主题交换机
     *
     * @return
     */
    @Bean
    public TopicExchange topicExchange() {
        return new TopicExchange("topicExchange", true, false);
    }


    /**
     * 头交换机
     *
     * @return
     */
    @Bean
    public HeadersExchange headersExchange() {
        return new HeadersExchange("headersExchange", true, false);
    }

}
