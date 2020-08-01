package com.holmes.springboot.security.service;

import com.holmes.springboot.security.entity.Permission;
import com.holmes.springboot.security.entity.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

@Service
public class UserService implements UserDetailsService {

    private static Map<Integer, User> userMap = new HashMap<>();

    private static Map<Integer, GrantedAuthority> permissionMap = new HashMap<>();

    static {
        /**
         * 权限默认 "ROLE_" 前缀
         */
        permissionMap.put(1, new Permission(1, "ROLE_ADMIN"));
        permissionMap.put(2, new Permission(2, "ROLE_USER"));
        permissionMap.put(2, new Permission(3, "ROLE_DBA"));
        Collection<GrantedAuthority> permissions = permissionMap.values();
        userMap.put(1, new User(1, "admin", "123456", permissions));
        userMap.put(2, new User(2, "zhangsan", "123456", new LinkedList<>()));
        userMap.put(3, new User(3, "lisi", "123456", new LinkedList<>()));
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        for (Map.Entry<Integer, User> entry : userMap.entrySet()) {
            if (username.equals(entry.getValue().getUsername())) {
                return entry.getValue();
            }
        }
        return null;
    }
}