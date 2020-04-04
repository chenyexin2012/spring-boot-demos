package com.holmes.springboot.dubbo.controller;

import com.holmes.springboot.dubbo.entity.User;
import com.holmes.springboot.dubbo.exception.BusinessException;
import com.holmes.springboot.dubbo.service.api.UserService;
import org.apache.dubbo.config.annotation.Reference;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("user")
public class UserController {

    @Reference(group = "userService", version = "1.0.0")
    private UserService userService;

    @GetMapping("/getUserList")
    public List<User> getUserList() throws BusinessException {
        return userService.getUserList();
    }

    @GetMapping("/getUser/{userId}")
    public User getUser(@PathVariable("userId") Integer userId) throws BusinessException {
        return userService.selectUserById(userId);
    }

    @PostMapping("/insert")
    public Boolean insert(@RequestBody User user) throws BusinessException {
        return userService.insert(user);
    }

    @DeleteMapping("/delete/{userId}")
    public Boolean deleteById(@PathVariable("userId") Integer userId) throws BusinessException {
        return userService.deleteById(userId);
    }
}
