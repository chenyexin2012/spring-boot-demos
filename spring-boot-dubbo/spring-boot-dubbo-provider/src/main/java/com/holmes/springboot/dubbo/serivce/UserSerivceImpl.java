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
import java.util.concurrent.atomic.AtomicInteger;

@Service(group = "userService", timeout = 30000, version = "1.0.0")
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

        List<User> userList = new ArrayList<>(USER_MAP.size());
        Collection<User> collection = USER_MAP.values();
        for(User user : collection) {
            userList.add(user);
        }
        return userList;
    }

    @Override
    public User selectUserById(Integer userId) throws BusinessException {
        return USER_MAP.get(userId);
    }

    @Override
    public Boolean insert(User user) throws BusinessException {
        USER_MAP.put(ID.incrementAndGet(), user);
        return true;
    }

    @Override
    public Boolean deleteById(Integer userId) throws BusinessException {
        USER_MAP.remove(userId);
        return true;
    }
}
