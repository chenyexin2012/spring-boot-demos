package com.holmes.springboot.security.controller;

import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
@RequestMapping("/user")
public class LoginController {

    @PostMapping(value = "/index1")
    @PreAuthorize("hasRole('ROLE_USER')")
    public String index1(@RequestBody JSONObject data) {
        log.info("index1: {}", data);
        return "success";
    }

    @PostMapping(value = "/index2")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public String index2(@RequestBody JSONObject data) {
        log.info("index2: {}", data);
        return "success";
    }

    @PostMapping(value = "/index3")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public String index3(@RequestBody JSONObject data) {
        log.info("index3: {}", data);
        return "success";
    }
}