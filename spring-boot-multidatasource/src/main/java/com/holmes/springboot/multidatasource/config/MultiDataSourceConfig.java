package com.holmes.springboot.multidatasource.config;

import com.alibaba.druid.pool.DruidDataSource;
import com.holmes.springboot.multidatasource.datasource.ThreadLocalRoutingDataSource;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

@Configuration
@MapperScan(basePackages = "com.holmes.springboot.multidatasource.mapper")
public class MultiDataSourceConfig {

    /**
     * 创建主数据源
     *
     * @return
     */
    @Bean
    @ConfigurationProperties(prefix = "spring.datasource.master")
    public DataSource masterDataSource() {
        // 使用DataSourceBuilder.create().build()无法加载druid数据源
        return new DruidDataSource();
    }

    /**
     * 创建从数据源
     *
     * @return
     */
    @Bean
    @ConfigurationProperties(prefix = "spring.datasource.slave")
    public DataSource slaveDataSource() {
        return new DruidDataSource();
    }

    @Bean
    public DataSource dataSource(@Qualifier("masterDataSource") DataSource master,
                                 @Qualifier("slaveDataSource") DataSource slave) {

        Map<Object, Object> map = new HashMap<>();
        map.put("master", master);
        map.put("slave", slave);
        ThreadLocalRoutingDataSource dataSource = new ThreadLocalRoutingDataSource();
        dataSource.setTargetDataSources(map);
        dataSource.setDefaultTargetDataSource(master);
        return dataSource;
    }

    @Bean
    public SqlSessionFactory sqlSessionFactory(DataSource dataSource) throws Exception {
        SqlSessionFactoryBean sqlSessionFactoryBean = new SqlSessionFactoryBean();
        sqlSessionFactoryBean.setDataSource(dataSource);
        sqlSessionFactoryBean.setConfigLocation(new ClassPathResource("mybatis/mybatis-config.xml"));
        return sqlSessionFactoryBean.getObject();
    }

    @Bean
    public SqlSessionTemplate sqlSessionTemplate(SqlSessionFactory sqlSessionFactory) throws Exception {
        return new SqlSessionTemplate(sqlSessionFactory);
    }
}
