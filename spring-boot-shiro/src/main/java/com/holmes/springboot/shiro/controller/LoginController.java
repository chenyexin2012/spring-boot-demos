package com.holmes.springboot.shiro.controller;

import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.subject.Subject;
import org.springframework.web.bind.annotation.*;

@RestController
@Slf4j
@RequestMapping("/user")
public class LoginController {

    @PostMapping(value = "/login")
    public String login(@RequestBody JSONObject data) {

        log.info("login: {}", data);
        String userName = data.getString("userName");
        String password = data.getString("password");
        Boolean rememberMe = data.getBoolean("rememberMe");

        Subject subject = SecurityUtils.getSubject();
        UsernamePasswordToken token = new UsernamePasswordToken(userName, password);
//        token.setRememberMe(rememberMe);
        try {
            subject.login(token);
        } catch (AuthenticationException e) {
            e.printStackTrace();
            return "{\"Msg\":\"您的账号或密码输入错误\",\"state\":\"failed\"}";
        }
        return "{\"Msg\":\"登陆成功\",\"state\":\"success\"}";
    }

    @PostMapping(value = "/index1")
    @RequiresPermissions(value = "/user/index1")
    public String index1(@RequestBody JSONObject data) {
        log.info("index1: {}", data);
        return "success";
    }

    @PostMapping(value = "/index2")
    @RequiresPermissions(value = "/user/index2")
    public String index2(@RequestBody JSONObject data) {
        log.info("index2: {}", data);
        return "success";
    }

    @PostMapping(value = "/index3")
    @RequiresPermissions(value = "/user/index2")
    public String index3(@RequestBody JSONObject data) {
        log.info("index3: {}", data);
        return "success";
    }
}