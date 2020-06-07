package com.holmes.springboot.rocketmq.consumer;

import com.holmes.springboot.rocketmq.constant.RocketmqConstant;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.common.message.MessageExt;
import org.apache.rocketmq.spring.annotation.ConsumeMode;
import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.annotation.SelectorType;
import org.apache.rocketmq.spring.core.RocketMQListener;
import org.springframework.stereotype.Component;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * 消息过滤
 *
 * 过滤类型有两种：TAG 和 SQL
 *
 * TAG: 每一个消息能够设置一个标签，消费者只接受指定TAG的消息，如"TagA || TagB || TagC"只接收包含这三种TAG的消息
 *
 *      生产者方法见 Producer.filterTagMessage
 *
 * SQL: 在使用TAG时，一个消息只能有一个标签，不能应对一些复杂的场景，此时可以使用SQL，在RocketMQ定义的语法下，可以实现一些简单的逻辑，如：
 *
 *     count BETWEEN 1 AND 6    // 生产者方法见 Producer.filterSQLMessage
 *
 *
 * SQL的基本语法：
 *
 *     数值比较，比如：>，>=，<，<=，BETWEEN，=；
 *     字符比较，比如：=，<>，IN；
 *     IS NULL 或者 IS NOT NULL；
 *     逻辑符号 AND，OR，NOT；
 *
 * 常量支持类型为：
 *
 *     数值，比如：123，3.1415；
 *     字符，比如：'abc'，必须用单引号包裹起来；
 *     NULL，特殊的常量
 *     布尔值，TRUE 或 FALSE
 *
 *  如果在使用SQL过滤时，出现如下异常信息：
 *      The broker does not support consumer to filter message by SQL92
 *  可通过在broker.conf文件中添加配置 enablePropertyFilter=true 来解决。
 */
@Component
@Slf4j
//@RocketMQMessageListener(topic = RocketmqConstant.TEST_FILTER_TOPIC, consumerGroup = "my-consumer-test-filter-topic",
//        selectorType = SelectorType.TAG, selectorExpression = "TagA || TagB || TagC")
@RocketMQMessageListener(topic = RocketmqConstant.TEST_FILTER_TOPIC, consumerGroup = "my-consumer-test-filter-topic",
        selectorType = SelectorType.SQL92, selectorExpression = "count BETWEEN 1 AND 6")
public class FilterConsumer implements RocketMQListener<MessageExt> {

    private static AtomicInteger count = new AtomicInteger(0);

    @Override
    public void onMessage(MessageExt message) {

        log.info("Thread: {}, tags: {}, message: {}, count: {}", Thread.currentThread().getId(), message.getTags(),
                new String(message.getBody()), count.incrementAndGet());
    }
}
