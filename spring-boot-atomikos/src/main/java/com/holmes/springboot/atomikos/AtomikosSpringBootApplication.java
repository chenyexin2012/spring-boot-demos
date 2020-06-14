package com.holmes.springboot.atomikos;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

/**
 * 使用Atomikos实现分布式事务
 *
 * 常规的做法是对每个数据源分别创建一个mybatis的SqlSessionFactory和mapper对象，从而保证不同的mapper能够使用不同的SqlSession，得以
 * 操作不同的数据源。
 *
 * 此处尝试使用同一个SqlSessionFactory和mapper来进行操作。参考多数据源整合的代码，可以发现在同一个事务中是无法切换数据源的，这是因为mybatis将
 * 事务托管给spring来完成的时候，只会创建一个SpringManagedTransaction对象，且会对dataSource和connection对象进行一个缓存复用。
 *
 * 此时需要对mybatis事务代码进行一点改造，见com.holmes.springboot.atomikos.datasource.transaction.SpringManagedTransactionFactory
 * 和com.holmes.springboot.atomikos.datasource.transaction.SpringManagedTransaction，保证在不同的数据源下会开启多个事务，
 * 改造的代码仅作为学习，不能用于实际环境中，可能会出现未知的异常。
 *
 *
 * 搭建过程中出现的异常及解决方法：
 *
 * 1. java.lang.NoSuchMethodException: com.mysql.cj.conf.PropertySet.getBooleanReadableProperty(java.lang.String)
 * 	    at java.lang.Class.getMethod(Class.java:1786)
 * 	    at com.alibaba.druid.util.MySqlUtils.createXAConnection(MySqlUtils.java:126)
 *
 * 	  进行代码MySqlUtils中可以发现druid连接池对mysql的xa支持版本最高为8.0.11，当mysql或mysql-connector-java过高时可能出现版本
 * 	  不兼容的问题，此时应当注意mysql或mysql-connector-java的版本，可尝试降低mysql-connector-java版本。
 *
 * 2. com.mysql.cj.jdbc.MysqlXAException: XAER_RMERR: Fatal error occurred in the transaction branch - check your data for consistency
 *
 *    出现这种问题可能是mysql未对当前用户开启XA事务支持。
 *
 *    可通过命令 SHOW GRANTS FOR root@'%'; 查看用户的账户权限，如root用户
 *
 *    执行命令 GRANT XA_RECOVER_ADMIN ON *.* TO root@'%'; 授予权限
 *
 *
 */
@SpringBootApplication(exclude = DataSourceAutoConfiguration.class)
public class AtomikosSpringBootApplication {

    public static void main(String[] args) {

        SpringApplication.run(AtomikosSpringBootApplication.class, args);
    }
}
