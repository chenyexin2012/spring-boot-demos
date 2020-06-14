/**
 * Copyright 2010-2019 the original author or authors.
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.holmes.springboot.atomikos.datasource.transaction;

import com.holmes.springboot.atomikos.datasource.DataSourceManager;
import com.holmes.springboot.atomikos.utils.SpringUtil;
import org.apache.ibatis.transaction.Transaction;
import org.mybatis.logging.Logger;
import org.mybatis.logging.LoggerFactory;
import org.springframework.jdbc.datasource.ConnectionHolder;
import org.springframework.jdbc.datasource.DataSourceUtils;
import org.springframework.transaction.support.TransactionSynchronizationManager;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

/**
 * {@code SpringManagedTransaction} handles the lifecycle of a JDBC connection. It retrieves a connection from Spring's
 * transaction manager and returns it back to it when it is no longer needed.
 * <p>
 * If Spring's transaction handling is active it will no-op all commit/rollback/close calls assuming that the Spring
 * transaction manager will do the job.
 * <p>
 * If it is not it will behave like {@code JdbcTransaction}.
 *
 * @author Hunter Presnall
 * @author Eduardo Macarron
 */
public class SpringManagedTransaction implements Transaction {

    private static final Logger LOGGER = LoggerFactory.getLogger(SpringManagedTransaction.class);

    private Map<String, Node> nodeMap = null;

    public SpringManagedTransaction() {
        nodeMap = new HashMap<>();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Connection getConnection() throws SQLException {
        // 获取当前使用的数据源key
        String sourceKey = DataSourceManager.get();
        Node node = nodeMap.get(sourceKey);
        if (node != null && node.connection != null) {
            return node.connection;
        }
        DataSource dataSource = SpringUtil.getBean(sourceKey);
        if (node == null) {
            node = new Node(dataSource);
            nodeMap.put(sourceKey, node);
        }
        if (node.connection == null) {
            openConnection(node);
        }
        return node.connection;
    }

    /**
     * Gets a connection from Spring transaction manager and discovers if this {@code Transaction} should manage
     * connection or let it to Spring.
     * <p>
     * It also reads autocommit setting because when using Spring Transaction MyBatis thinks that autocommit is always
     * false and will always call commit/rollback so we need to no-op that calls.
     */
    private void openConnection(Node node) throws SQLException {
        node.connection = DataSourceUtils.getConnection(node.dataSource);
        node.autoCommit = node.connection.getAutoCommit();
        node.isConnectionTransactional = DataSourceUtils.isConnectionTransactional(node.connection, node.dataSource);

        LOGGER.debug(() -> "JDBC Connection [" + node.connection + "] will"
                + (node.isConnectionTransactional ? " " : " not ") + "be managed by Spring");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void commit() throws SQLException {

        // 实际由Spring管理事务时，下面的onnection.commit();方法并不会执行，rollback同理。
        for (Node node : this.nodeMap.values()) {
            if (node.connection != null && !node.isConnectionTransactional && !node.autoCommit) {
                LOGGER.debug(() -> "Committing JDBC Connection [" + node.connection + "]");
                node.connection.commit();
            }
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void rollback() throws SQLException {
        for (Node node : this.nodeMap.values()) {
            if (node.connection != null && !node.isConnectionTransactional && !node.autoCommit) {
                LOGGER.debug(() -> "Rolling back JDBC Connection [" + node.connection + "]");
                node.connection.rollback();
            }
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void close() {
        for (Node node : this.nodeMap.values()) {
            DataSourceUtils.releaseConnection(node.connection, node.dataSource);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Integer getTimeout() {
        String sourceKey = DataSourceManager.get();
        Node node = nodeMap.get(sourceKey);
        if(node != null) {
            ConnectionHolder holder = (ConnectionHolder) TransactionSynchronizationManager.getResource(node.dataSource);
            if (holder != null && holder.hasTimeout()) {
                return holder.getTimeToLiveInSeconds();
            }
        }
        return null;
    }

    private static class Node {

        private final DataSource dataSource;

        private Connection connection;

        private boolean isConnectionTransactional;

        private boolean autoCommit;

        Node(DataSource dataSource) {
            this.dataSource = dataSource;
        }
    }
}
