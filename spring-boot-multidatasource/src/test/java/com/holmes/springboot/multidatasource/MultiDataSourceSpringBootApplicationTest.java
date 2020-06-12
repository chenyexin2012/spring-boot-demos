package com.holmes.springboot.multidatasource;

import com.holmes.springboot.multidatasource.entity.User;
import com.holmes.springboot.multidatasource.service.UserService;
import com.holmes.springboot.multidatasource.utils.DataGeneratorUtil;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

@SpringBootTest
@RunWith(SpringRunner.class)
@Slf4j
public class MultiDataSourceSpringBootApplicationTest {

    @Autowired
    private UserService userService;

    @Test
    public void testSelectList() {

        List<User> list = userService.selectList(1, 10);
        log.info("size = {}", list.size());
        for (User user : list) {
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
            if (userService.insert(user) == 1) {
                log.info(user.toString() + "添加成功！");
            } else {
                log.info(user.toString() + "添加失败！");
            }
        }
    }

    @Test
    public void testDelete() {
        log.info("delete: {}", userService.deleteById(1L));
    }

    @Test
    public void testUpdate() {

        User user = userService.selectById(1L);
        log.info("user: {}", user);
        if (user != null) {
            user.setAddress("北京市朝阳区");
            log.info("update: {}", userService.update(user));
        }
    }

}
