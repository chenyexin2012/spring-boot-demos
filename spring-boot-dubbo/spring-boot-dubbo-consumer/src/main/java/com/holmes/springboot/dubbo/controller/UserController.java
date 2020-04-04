package com.holmes.springboot.dubbo.controller;

import com.alibaba.fastjson.JSONObject;
import com.holmes.springboot.dubbo.entity.User;
import com.holmes.springboot.dubbo.exception.BusinessException;
import com.holmes.springboot.dubbo.exception.NullInputException;
import com.holmes.springboot.dubbo.service.api.IService;
import com.holmes.springboot.dubbo.service.api.UserService;
import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.config.annotation.Reference;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.Collections;
import java.util.List;

@RestController
@RequestMapping("user")
@Slf4j
public class UserController {

    @Reference(group = "userService", version = "1.0.0", retries = 3)
    private UserService userService;

    @Reference(group = "helloService", version = "1.0.0")
    private IService iService;

    @GetMapping("/hello/{name}")
    public String hello(@PathVariable("name") String name) {

        JSONObject jsonObject = new JSONObject();
        jsonObject.put("name", name);
        try {
            return iService.handle(jsonObject).toJSONString();
        } catch (NullInputException | BusinessException e) {
            log.error("", e);
        }
        return "failed";
    }

    @GetMapping("/getUserList")
    public List<User> getUserList() {
        try {
            return userService.getUserList();
        } catch (BusinessException e) {
            log.error("", e);
        }
        return Collections.EMPTY_LIST;
    }

    @GetMapping("/getUser/{userId}")
    public User getUser(@PathVariable("userId") Integer userId) {
        try {
            return userService.selectUserById(userId);
        } catch (BusinessException e) {
            log.error("", e);
        }
        return null;
    }

    @PostMapping("/insert")
    public Boolean insert(@RequestBody User user) {
        try {
            return userService.insert(user);
        } catch (BusinessException e) {
            log.error("", e);
        }
        return false;
    }

    @DeleteMapping("/delete/{userId}")
    public Boolean deleteById(@PathVariable("userId") Integer userId) {
        try {
            return userService.deleteById(userId);
        } catch (BusinessException e) {
            log.error("", e);
        }
        return false;
    }
}
