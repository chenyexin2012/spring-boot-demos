package com.holmes.springboot.mybatisplus;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.holmes.springboot.mybatisplus.entity.User;
import com.holmes.springboot.mybatisplus.service.IUserService;
import com.holmes.springboot.mybatisplus.utils.DataGeneratorUtil;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.util.LinkedList;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
@Slf4j
public class MybatisSpringBootApplicationTest {

    @Resource
    private IUserService userService;

    @Test
    public void testSelect() {
        System.out.println(userService.getById(101L));
    }

    @Test
    public void testSelectByCondition() {

        User user = new User();
        user.setUserName("钦飞厚");

        QueryWrapper<User> condition = new QueryWrapper<>();
        condition.setEntity(user);

        List<User> list = userService.list(condition);
        log.info("size = {}", list.size());
        for (User entity : list) {
            log.info(entity.toString());
        }
    }

    @Test
    public void testSelectList() {

        List<User> list = userService.list();
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
            if (userService.save(user)) {
                log.info(user.toString() + "添加成功！");
            } else {
                log.info(user.toString() + "添加失败！");
            }
        }
    }

    @Test
    public void testUpdate() {

        User user = new User();
        user.setId(101L);
        user.setUserName(DataGeneratorUtil.generateName());

        if (userService.updateById(user)) {
            log.info(user.toString() + "修改成功！");
        } else {
            log.info(user.toString() + "修改失败！");
        }
    }

    @Test
    public void testDelete() {

        List<User> list = userService.list();
        List<Long> idList = new LinkedList<>();
        for (User user : list) {
            idList.add(user.getId());
        }
        userService.removeByIds(idList);
    }
}
