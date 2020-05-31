package com.holmes.springboot.shardingsphere;

import com.holmes.springboot.shardingsphere.entity.Order;
import com.holmes.springboot.shardingsphere.entity.User;
import com.holmes.springboot.shardingsphere.utils.DataGeneratorUtil;
import com.holmes.springboot.shardingsphere.service.OrderService;
import com.holmes.springboot.shardingsphere.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicLong;

@RunWith(SpringRunner.class)
@SpringBootTest
@Slf4j
public class ShardingSphereSpringBootApplicationTest {

    private static ExecutorService executorService = Executors.newCachedThreadPool();

    private static AtomicLong success = new AtomicLong(0);

    private static AtomicLong failed = new AtomicLong(0);

    @Autowired
    private UserService userService;

    @Autowired
    private OrderService orderService;

    @Test
    public void testSelectUserList() {
        List<User> list = userService.selectList();
        System.out.println(list.size());
    }

    @Test
    public void testSelectUserById() {
        User user = userService.selectById(0L);
        System.out.println(user);
    }

    @Test
    public void testInsertOne() {

        User user = new User();
        user.setUsername(DataGeneratorUtil.generateName());
        user.setGender(0);
        user.setPhone(DataGeneratorUtil.generatePhoneNumber());
        user.setPassword("123456");
        user.setAddress(DataGeneratorUtil.generateAddress());
        user.setCreateTime(new Date());
        user.setModifyTime(new Date());
        userService.insert(user);
    }

    @Test
    public void testInsertUser() throws InterruptedException {
        long start = System.currentTimeMillis();
        for(int i = 0; i < 10; i++) {
            executorService.execute(() -> {
                for (int j = 0; j < 100; j++) {
                    User user = new User();
                    user.setUsername(DataGeneratorUtil.generateName());
                    user.setGender(0);
                    user.setPhone(DataGeneratorUtil.generatePhoneNumber());
                    user.setPassword("123456");
                    user.setAddress(DataGeneratorUtil.generateAddress());
                    user.setCreateTime(new Date());
                    user.setModifyTime(new Date());
                    if(userService.insert(user) == 1) {
                        success.incrementAndGet();
//                        log.info(user.toString() + "添加成功！");
                    } else {
                        failed.incrementAndGet();
//                        log.info(user.toString() + "添加失败！");
                    }
                }
            });
        }

        executorService.shutdown();
        while(!executorService.awaitTermination(1, TimeUnit.SECONDS)) {
        }
        long end = System.currentTimeMillis();

        log.info("新增{}条，成功{}， 失败{}，耗时{}ms", 1000, success.get(), failed.get(), end - start);
    }

    @Test
    public void testClearUserTable() {
        userService.clearTable();
    }

    @Test
    public void testSelectOrderList() {

        List<Order> list = orderService.selectList();
        System.out.println(list.size());
    }

    @Test
    public void testSelectUserOrderList() {
        List<Map<String, Object>> list = orderService.selectUserOrderList(1L);
        System.out.println(list.size());
    }

    @Test
    public void testSelectOrderById() {

        Order order = orderService.selectById(1L);
        System.out.println(order);
    }

    @Test
    public void testSelectOrderByUserId() {

        List<Order> list = orderService.selectByUserId(1L);
        System.out.println(list);
    }

    @Test
    public void insertOrder() {

        Random random = new Random();
        for (int i = 0; i < 1000; i++) {
            Order order = new Order();
            order.setUserId(Math.abs(random.nextLong() % 1000));
            order.setOrderSn(UUID.randomUUID().toString());
            order.setStatus(0);
            order.setCreateTime(new Date());
            order.setModifyTime(new Date());
            log.info(order.toString());
            if(orderService.insert(order) == 1) {
                log.info(order.toString() + "添加成功！");
            } else {
                log.info(order.toString() + "添加失败！");
            }
        }
    }

    @Test
    public void testClearOrderTable() {
        orderService.clearTable();
    }

}
