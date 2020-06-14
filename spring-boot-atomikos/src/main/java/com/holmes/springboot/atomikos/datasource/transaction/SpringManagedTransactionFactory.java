/**
 * Copyright 2010-2019 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.holmes.springboot.atomikos.datasource.transaction;

import org.apache.ibatis.session.TransactionIsolationLevel;
import org.apache.ibatis.transaction.Transaction;

import javax.sql.DataSource;

/**
 *
 * 此处必须继承自org.mybatis.spring.transaction.SpringManagedTransactionFactory
 *
 * 原因见代码 org.mybatis.spring.SqlSessionUtils --> environment.getTransactionFactory() instanceof SpringManagedTransactionFactory
 *
 * private static void registerSessionHolder(SqlSessionFactory sessionFactory, ExecutorType executorType,
 *       PersistenceExceptionTranslator exceptionTranslator, SqlSession session) {
 *     SqlSessionHolder holder;
 *     if (TransactionSynchronizationManager.isSynchronizationActive()) {
 *       Environment environment = sessionFactory.getConfiguration().getEnvironment();
 *
 *       if (environment.getTransactionFactory() instanceof SpringManagedTransactionFactory) {
 *         LOGGER.debug(() -> "Registering transaction synchronization for SqlSession [" + session + "]");
 *
 *         holder = new SqlSessionHolder(session, executorType, exceptionTranslator);
 *         TransactionSynchronizationManager.bindResource(sessionFactory, holder);
 *         TransactionSynchronizationManager
 *             .registerSynchronization(new SqlSessionSynchronization(holder, sessionFactory));
 *         holder.setSynchronizedWithTransaction(true);
 *         holder.requested();
 *       } else {
 *         if (TransactionSynchronizationManager.getResource(environment.getDataSource()) == null) {
 *           LOGGER.debug(() -> "SqlSession [" + session
 *               + "] was not registered for synchronization because DataSource is not transactional");
 *         } else {
 *           throw new TransientDataAccessResourceException(
 *               "SqlSessionFactory must be using a SpringManagedTransactionFactory in order to use Spring transaction synchronization");
 *         }
 *       }
 *     } else {
 *       LOGGER.debug(() -> "SqlSession [" + session
 *           + "] was not registered for synchronization because synchronization is not active");
 *     }
 *
 *   }
 *
 * Creates a {@code SpringManagedTransaction}.
 *
 * @author Hunter Presnall
 */
public class SpringManagedTransactionFactory extends org.mybatis.spring.transaction.SpringManagedTransactionFactory {

  /**
   * {@inheritDoc}
   */
  @Override
  public Transaction newTransaction(DataSource dataSource, TransactionIsolationLevel level, boolean autoCommit) {
    return new SpringManagedTransaction();
  }

}
