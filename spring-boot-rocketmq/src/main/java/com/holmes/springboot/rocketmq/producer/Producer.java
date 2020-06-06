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
     * 发送顺序消息
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
}
