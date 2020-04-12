package com.holmes.springboot.redis;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class BaseSpringBootApplicationTest {

    @Autowired
    protected ApplicationContext applicationContext;

    @Test
    public void test() {
        System.out.println(applicationContext);
    }
}
