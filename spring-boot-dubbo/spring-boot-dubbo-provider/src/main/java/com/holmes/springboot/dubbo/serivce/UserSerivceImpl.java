package com.holmes.springboot.dubbo.serivce;

import com.holmes.springboot.dubbo.entity.Address;
import com.holmes.springboot.dubbo.entity.User;
import com.holmes.springboot.dubbo.exception.BusinessException;
import com.holmes.springboot.dubbo.service.api.UserService;
import com.holmes.springboot.dubbo.utils.DataGeneratorUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.config.annotation.Service;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

@Service(group = "userService", timeout = 3000, version = "1.0.0")
@Slf4j
public class UserSerivceImpl implements UserService {

    private static Map<Integer, User> USER_MAP = new ConcurrentHashMap<>();

    private static AtomicInteger ID = null;

    static {
        Random random = new Random();
        for (int i = 0; i < 10; i++) {
            USER_MAP.put(i, new User(i, DataGeneratorUtil.generateName(),
                    random.nextInt(20) + 16, new Address("浙江省", "杭州市", "滨江区")));
        }
        ID = new AtomicInteger(USER_MAP.size());
    }

    @Override
    public List<User> getUserList() throws BusinessException {

        try {
            log.info("获取用户列表");
            double random = Math.random();
            if (random > 0.8) {
                // 模拟异常
                log.info("程序发生了异常");
                int i = 1 / 0;
            }
            if (random > 0.6) {
                // 模拟超时
                TimeUnit.SECONDS.sleep(5);
                log.info("程序运行超时");
            }

            List<User> userList = new ArrayList<>(USER_MAP.size());
            Collection<User> collection = USER_MAP.values();
            for (User user : collection) {
                userList.add(user);
            }
            return userList;
        } catch (Exception e) {
            log.error("获取用户列表失败", e);
            throw new BusinessException("获取用户列表失败", e);
        }
    }

    @Override
    public User selectUserById(Integer userId) throws BusinessException {
        try {
            log.info("获取用户信息, userId={}", userId);
            return USER_MAP.get(userId);
        } catch (Exception e) {
            log.error("获取用户信息失败", e);
            throw new BusinessException("获取用户信息失败", e);
        }
    }

    @Override
    public Boolean insert(User user) throws BusinessException {
        try {
            log.info("新增用户信息, user={}", user);
            USER_MAP.put(ID.incrementAndGet(), user);
            return true;
        } catch (Exception e) {
            log.error("新增用户信息失败", e);
            throw new BusinessException("新增用户信息失败", e);
        }
    }

    @Override
    public Boolean deleteById(Integer userId) throws BusinessException {
        try {
            log.info("删除用户信息, userId={}", userId);
            USER_MAP.remove(userId);
            return true;
        } catch (Exception e) {
            log.error("删除用户信息失败", e);
            throw new BusinessException("删除用户信息失败", e);
        }
    }
}
