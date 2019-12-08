package com.holmes.springboot.shiro.service;

import com.holmes.springboot.shiro.entity.Permission;
import com.holmes.springboot.shiro.entity.User;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class ShiroService {

    private static Map<Integer, User> userMap = new HashMap<>();

    private static Map<Integer, Permission> permissionMap = new HashMap<>();

    static {
        permissionMap.put(1, new Permission(1, "/user/index1"));
        permissionMap.put(2, new Permission(2, "/user/index2"));
        permissionMap.put(3, new Permission(3, "/user/index3"));
        Collection<Permission> permissions = permissionMap.values();
        userMap.put(1, new User(1, "admin", "123456", permissions));
        userMap.put(2, new User(2, "zhangsan", "123456", new LinkedList<>()));
        userMap.put(3, new User(3, "lisi", "123456", new LinkedList<>()));
    }

    public User getUserByName(String userName) {
        for (Map.Entry<Integer, User> entry : userMap.entrySet()) {
            if (userName.equals(entry.getValue().getUsername())) {
                return entry.getValue();
            }
        }
        return null;
    }
}