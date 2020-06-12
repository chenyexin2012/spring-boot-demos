package com.holmes.springboot.multidatasource;

import com.holmes.springboot.multidatasource.service.UserService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.context.ApplicationContext;

@SpringBootApplication(exclude = DataSourceAutoConfiguration.class)
public class MultiDataSourceSpringBootApplication {

    public static void main(String[] args) {
        ApplicationContext context = SpringApplication.run(MultiDataSourceSpringBootApplication.class);
        UserService userService = context.getBean(UserService.class);

        System.out.println(userService.selectList(1, 10));
    }
}