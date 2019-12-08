package com.holmes.springboot.shiro.mybatis;

import com.holmes.springboot.mybatis.entity.User;
import com.holmes.springboot.mybatis.service.UserService;
import com.holmes.springboot.mybatis.utils.DataGeneratorUtil;
import com.holmes.springboot.shiro.mybatis.entity.User;
import com.holmes.springboot.shiro.mybatis.utils.DataGeneratorUtil;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
@Slf4j
public class MybatisSpringBootApplicationTest {

    @Autowired
    private UserService userService;

    @Test
    public void testSelectList() {

        List<User> list = userService.selectList(6, 10);
        log.info("size = {}", list.size());
        for(User user : list) {
            log.info(user.toString());
        }
    }

    @Test
    public void testInsertUser() {
        for (int i = 0; i < 100; i++) {
            User user = new User();
            user.setUserName(DataGeneratorUtil.generateName());
            user.setUserPassword("123456");
            user.setAddress(DataGeneratorUtil.generateAddress());
            user.setUserEmail(DataGeneratorUtil.generateEmailAddress());
            if(userService.insert(user) == 1) {
                log.info(user.toString() + "添加成功！");
            } else {
                log.info(user.toString() + "添加失败！");
            }
        }
    }
}
