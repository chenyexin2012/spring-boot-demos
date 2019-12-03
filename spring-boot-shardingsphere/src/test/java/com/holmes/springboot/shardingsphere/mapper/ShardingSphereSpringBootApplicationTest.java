package com.holmes.springboot.shardingsphere.mapper;

import com.holmes.springboot.shardingsphere.entity.User;
import com.holmes.springboot.shardingsphere.mapper.utils.DataGeneratorUtil;
import com.holmes.springboot.shardingsphere.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Random;

@RunWith(SpringRunner.class)
@SpringBootTest
@Slf4j
public class ShardingSphereSpringBootApplicationTest {

    private String[] genders = new String[] {"female", "male"};

    @Autowired
    private UserService userService;

    @Test
    public void testSelectList() {
        System.out.println(userService.selectList());
    }

    @Test
    public void testInsertUser() {
        Random random = new Random();
        for (int i = 0; i < 100; i++) {
            User user = new User();
            user.setName(DataGeneratorUtil.generateName());
            user.setAge(random.nextInt(30) + 10);
            user.setGender(genders[random.nextInt(2)]);
            user.setCity(DataGeneratorUtil.generateAddress());
            if(userService.insert(user) == 1) {
                log.info(user.toString() + "添加成功！");
            } else {
                log.info(user.toString() + "添加失败！");
            }
        }
    }
}
