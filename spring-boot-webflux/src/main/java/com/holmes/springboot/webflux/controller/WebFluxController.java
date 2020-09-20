package com.holmes.springboot.webflux.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

import java.util.concurrent.TimeUnit;

@RestController
@RequestMapping("/webflux")
public class WebFluxController {

    @GetMapping("/hello")
    public Mono<String> hello(String name) {
        try {
            TimeUnit.MILLISECONDS.sleep(100);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return Mono.just(String.format("Hello, name=%s", name));
    }

//    @GetMapping("/hello")
//    public String hello(String name) {
//        try {
//            TimeUnit.MILLISECONDS.sleep(100);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
//        return String.format("Hello, name=%s", name);
//    }
}
