package com.holmes.springboot.cache.controller;

import com.holmes.springboot.cache.entity.User;
import com.holmes.springboot.cache.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/selectList")
    public List<User> selectList(Integer startPage, Integer pageSize) {
        return userService.selectList(startPage, pageSize);
    }

    @DeleteMapping("/deleteById/{id}")
    public int deleteById(@PathVariable("id") Long id) {
        return userService.deleteById(id);
    }

    @PutMapping("/insert")
    public User insert(@RequestBody User record) {
        return userService.insert(record);
    }

    @GetMapping("/selectById")
    public User selectById(Long id) {
        return userService.selectById(id);
    }

    @PostMapping("/update")
    public User update(@RequestBody User record) {
        return userService.update(record);
    }
}
