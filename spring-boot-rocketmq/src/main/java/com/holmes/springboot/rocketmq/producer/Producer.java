package com.holmes.springboot.rocketmq.producer;

import com.holmes.springboot.rocketmq.constant.RocketmqConstant;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.client.exception.MQBrokerException;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.client.producer.*;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.common.message.MessageQueue;
import org.apache.rocketmq.remoting.exception.RemotingException;
import org.apache.rocketmq.spring.core.RocketMQTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

@Component
@Slf4j
public class Producer {

    private static AtomicInteger count = new AtomicInteger(0);

    @Autowired
    private RocketMQTemplate rocketMQTemplate;

    /**
     * 发送同步消息，这种方式比较可靠，用途比较广泛，通常用于某些重要的通知
     */
    public void syncMessage() {

        try {
            String msg = "TEST ROCKET MQ MESSAGE, COUNT=" + count.incrementAndGet();
            Message message = new Message(RocketmqConstant.TEST_TOPIC, msg.getBytes());
            DefaultMQProducer producer = rocketMQTemplate.getProducer();
            SendResult result = producer.send(message);

            if (SendStatus.SEND_OK == result.getSendStatus()) {
//                log.info("result: {}", result);
            } else {
                log.info("failed: {}", result);
            }
        } catch (MQClientException e) {
            e.printStackTrace();
        } catch (RemotingException e) {
            e.printStackTrace();
        } catch (MQBrokerException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    /**
     * 发送异步消息，通常用于对响应时间敏感的消息
     */
    public void asyncMessage() {
        try {
            String msg = "TEST ROCKET MQ MESSAGE, COUNT=" + count.incrementAndGet();
            Message message = new Message(RocketmqConstant.TEST_TOPIC, msg.getBytes());
            DefaultMQProducer producer = rocketMQTemplate.getProducer();
            producer.send(message, new SendCallback() {
                @Override
                public void onSuccess(SendResult sendResult) {
//                    log.info("result: {}", sendResult);
                }
                @Override
                public void onException(Throwable e) {
                    log.error("error", e);
                }
            });
        } catch (MQClientException e) {
            e.printStackTrace();
        } catch (RemotingException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    /**
     * 单向发送消息，而不考虑发送结果，例如日志发送
     */
    public void oneWayMessage() {
        try {
            String msg = "TEST ROCKET MQ MESSAGE, COUNT=" + count.incrementAndGet();
            Message message = new Message(RocketmqConstant.TEST_TOPIC, msg.getBytes());
            DefaultMQProducer producer = rocketMQTemplate.getProducer();
            producer.sendOneway(message);
        } catch (MQClientException e) {
            e.printStackTrace();
        } catch (RemotingException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    /**
     * 延迟消息
     *
     * rocketmq不支持随意的设置延迟时间，而是通过设置几个固定的延迟等级
     *
     * "1s 5s 10s 30s 1m 2m 3m 4m 5m 6m 7m 8m 9m 10m 20m 30m 1h 2h"
     *
     * 一般应用场景：进行某个操作之后，等待一定时间后，进行另一个操作，如：
     * 提交了一个订单并发送一个延时消息，1h后检查这个订单的状态，如果还是未付款就取消订单释放库存。
     */
    public void scheduledMessage() {

        try {
            String msg = "TEST ROCKET MQ MESSAGE, COUNT=" + count.incrementAndGet();
            Message message = new Message(RocketmqConstant.TEST_TOPIC, msg.getBytes());
            // 设置延时等级3，这个消息将在10s之后发送
            message.setDelayTimeLevel(3);
            DefaultMQProducer producer = rocketMQTemplate.getProducer();
            SendResult result = producer.send(message);

            if (SendStatus.SEND_OK == result.getSendStatus()) {
                log.info("result: {}", result);
            } else {
                log.info("failed: {}", result);
            }
        } catch (MQClientException e) {
            e.printStackTrace();
        } catch (RemotingException e) {
            e.printStackTrace();
        } catch (MQBrokerException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    /**
     * 发送顺序消息
     *
     * 消费者见 OrderedConsumer
     */
    public void orderMessage() {

        try {
            int id = count.incrementAndGet();
            String msg = "TEST ROCKET MQ MESSAGE, COUNT=" + id;
            Message message = new Message(RocketmqConstant.TEST_ORDER_TOPIC, msg.getBytes());
            DefaultMQProducer producer = rocketMQTemplate.getProducer();
            SendResult result = producer.send(message, new MessageQueueSelector() {
                @Override
                public MessageQueue select(List<MessageQueue> mqs, Message msg, Object arg) {
                    Integer id = (Integer) arg;
                    int size = mqs.size();
                    // 通过 id 将消息发送到不同的 queue 上，
                    // 而在消费端，每个 queue 都会有一个独立的 consumer 来消费，
                    // 从而保证顺序消费
                    // 实际使用中可以通过业务的编号实现同一业务的顺序消费
                    return mqs.get(id % size);
                }
            }, id);

            if (SendStatus.SEND_OK == result.getSendStatus()) {
//                log.info("result: {}", result);
            } else {
                log.info("failed: {}", result);
            }
        } catch (MQClientException e) {
            e.printStackTrace();
        } catch (RemotingException e) {
            e.printStackTrace();
        } catch (MQBrokerException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    /**
     * 批量发送消息，send 方法可直接发送一个消息集合
     *
     * 默认每次发送不能超过4M
     *
     */
    public void batchMessage() {

        try {
            String msg = "TEST ROCKET MQ MESSAGE, COUNT=" + count.incrementAndGet();
            Message message = new Message(RocketmqConstant.TEST_TOPIC, msg.getBytes());
            List<Message> list = new ArrayList<>(10);
            for (int i = 0; i < 10; i++) {
                list.add(message);
            }
            DefaultMQProducer producer = rocketMQTemplate.getProducer();
            SendResult result = producer.send(list);

            if (SendStatus.SEND_OK == result.getSendStatus()) {
                log.info("result: {}", result);
            } else {
                log.info("failed: {}", result);
            }
        } catch (MQClientException e) {
            e.printStackTrace();
        } catch (RemotingException e) {
            e.printStackTrace();
        } catch (MQBrokerException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    /**
     * 消息过滤，消费端使用tag进行过滤
     *
     * 消费者见 FilterConsumer
     */
    public void filterTagMessage() {

        try {
            String[] tags = new String[] {"TagA", "TagB", "TagC", "TagD", "TagE"};
            String msg = "TEST ROCKET MQ MESSAGE, COUNT=" + count.incrementAndGet();
            Message message = new Message(RocketmqConstant.TEST_FILTER_TOPIC, tags[count.get() % tags.length], msg.getBytes());
            DefaultMQProducer producer = rocketMQTemplate.getProducer();
            SendResult result = producer.send(message);

            if (SendStatus.SEND_OK == result.getSendStatus()) {
//                log.info("result: {}", result);
            } else {
                log.info("failed: {}", result);
            }
        } catch (MQClientException e) {
            e.printStackTrace();
        } catch (RemotingException e) {
            e.printStackTrace();
        } catch (MQBrokerException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    /**
     * 消息过滤，消费端使用sql语法进行过滤
     *
     * 消费者见 FilterConsumer
     */
    public void filterSQLMessage() {

        try {
            String msg = "TEST ROCKET MQ MESSAGE, COUNT=" + count.incrementAndGet();
            Message message = new Message(RocketmqConstant.TEST_FILTER_TOPIC, msg.getBytes());
            // 设置属性，消费端将根据属性值进行过滤
            message.putUserProperty("count", String.valueOf(count.get() % 10));
            DefaultMQProducer producer = rocketMQTemplate.getProducer();
            SendResult result = producer.send(message);

            if (SendStatus.SEND_OK == result.getSendStatus()) {
//                log.info("result: {}", result);
            } else {
                log.info("failed: {}", result);
            }
        } catch (MQClientException e) {
            e.printStackTrace();
        } catch (RemotingException e) {
            e.printStackTrace();
        } catch (MQBrokerException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
