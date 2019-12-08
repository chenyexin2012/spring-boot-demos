package com.holmes.springboot.shiro.mybatis;

import com.holmes.springboot.mybatis.service.UserService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

@SpringBootApplication
public class MybatisSpringBootApplication {

    public static void main(String[] args) {

        ApplicationContext context = SpringApplication.run(MybatisSpringBootApplication.class, args);

        UserService userService = context.getBean(UserService.class);

        System.out.println(userService.selectList(1, 10));
    }
}
