package com.holmes.springboot.rocketmq.producer;

import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.spring.annotation.RocketMQTransactionListener;
import org.apache.rocketmq.spring.core.RocketMQLocalTransactionListener;
import org.apache.rocketmq.spring.core.RocketMQLocalTransactionState;
import org.springframework.messaging.Message;
import org.springframework.stereotype.Component;

/**
 *
 * 事务监听接口
 *
 * 注：@RocketMQTransactionListener 注解用于生产者方，而不是用于消费者方，这点与@RocketMQMessageListener不同
 *
 * 事务消息共有三种状态，分别是提交状态、回滚状态、中间状态：
 *
 *     TransactionStatus.CommitTransaction: 提交事务，它允许消费者消费此消息。
 *     TransactionStatus.RollbackTransaction: 回滚事务，它代表该消息将被删除，不允许被消费。
 *     TransactionStatus.Unknown: 中间状态，它代表需要检查消息队列来确定状态。
 *
 * 当发送半消息成功时，我们使用 executeLocalTransaction 方法来执行本地事务，并根据实际的执行结果返回一个事务状态。
 * checkLocalTransaction 方法用于检查本地事务状态，并回应消息队列的检查请求，同样返回一个事务状态。
 *
 * 事务消息使用上的限制
 *
 *     1. 事务消息不支持延时消息和批量消息。
 *     2. 为了避免单个消息被检查太多次而导致半队列消息累积，我们默认将单个消息的检查次数限制为 15 次，
 *        但是用户可以通过 Broker 配置文件的 transactionCheckMax参数来修改此限制。
 *        如果已经检查某条消息超过 N 次的话（ N = transactionCheckMax ） 则 Broker 将丢弃此消息，并在默认情况下同时打印错误日志。
 *        用户可以通过重写 AbstractTransactionCheckListener 类来修改这个行为。
 *     3. 事务消息将在 Broker 配置文件中的参数 transactionMsgTimeout 这样的特定时间长度之后被检查。
 *        当发送事务消息时，用户还可以通过设置用户属性 CHECK_IMMUNITY_TIME_IN_SECONDS 来改变这个限制，该参数优先于 transactionMsgTimeout 参数。
 *     4. 事务性消息可能不止一次被检查或消费。
 *     5. 提交给用户的目标主题消息可能会失败，目前这依日志的记录而定。它的高可用性通过 RocketMQ 本身的高可用性机制来保证，
 *        如果希望确保事务消息不丢失、并且事务完整性得到保证，建议使用同步的双重写入机制。
 *     6. 事务消息的生产者 ID 不能与其他类型消息的生产者 ID 共享。与其他类型的消息不同，事务消息允许反向查询、MQ服务器能通过它们的生产者 ID 查询到消费者。
 */
@Component
@Slf4j
@RocketMQTransactionListener()
public class TransactionMessageListener implements RocketMQLocalTransactionListener {

    @Override
    public RocketMQLocalTransactionState executeLocalTransaction(Message msg, Object arg) {
        // 执行一个本地事务，并返回一个事务状态
        try {
            log.info("message: {}, arg: {}", new String((byte[]) msg.getPayload()), arg);
            // 模拟异常
            // int i = 1 / 0;
            // 执行本地事务
            // do something
            // 提交事务
            // do something
            log.info("本地事务执行完毕");
            return RocketMQLocalTransactionState.UNKNOWN;
        } catch (Exception e) {
            // 回滚本地事务
            // do something
            log.error("本地事务执行发生异常", e);
            return RocketMQLocalTransactionState.ROLLBACK;
        }
    }

    @Override
    public RocketMQLocalTransactionState checkLocalTransaction(Message msg) {

        log.info("执行检查任务, message: {}", new String((byte[]) msg.getPayload()));
        // do something

        return RocketMQLocalTransactionState.COMMIT;
    }
}
