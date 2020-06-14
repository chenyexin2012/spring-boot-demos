package com.holmes.springboot.atomikos;

import com.holmes.springboot.atomikos.entity.User;
import com.holmes.springboot.atomikos.service.UserService;
import com.holmes.springboot.atomikos.utils.DataGeneratorUtil;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.TransactionManager;

import javax.sql.DataSource;
import java.util.Map;

@SpringBootTest
@RunWith(SpringRunner.class)
@Slf4j
public class AtomikosSpringBootApplicationTest {

    @Autowired
    private ApplicationContext applicationContext;

    @Autowired
    private UserService userService;

    @Test
    public void test() {

        Map<String, DataSource> dataSourceMap = applicationContext.getBeansOfType(DataSource.class);
        dataSourceMap.forEach((name, datasource) -> {
            log.info("name: {}, datasource: {}", name, datasource);
        });

        Map<String, TransactionManager> transactionManagerMap = applicationContext.getBeansOfType(TransactionManager.class);
        transactionManagerMap.forEach((name, transactionManager) -> {
            log.info("name: {}, transactionManager: {}", name, transactionManager);
        });
    }

    @Test
    public void testInsert() {

        long start = System.currentTimeMillis();
        for (int i = 0; i < 10000; i++) {
            try {
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
            } catch (Exception e) {
                log.error("", e);
            }
        }
        System.out.println(System.currentTimeMillis() - start);
    }

}
