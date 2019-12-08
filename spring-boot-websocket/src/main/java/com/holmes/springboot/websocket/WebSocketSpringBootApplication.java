package com.holmes.springboot.websocket;

import com.holmes.springboot.websocket.socket.WebSocketCache;
import com.holmes.springboot.websocket.socket.WebSocketHandler;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

@SpringBootApplication
@EnableScheduling
public class WebSocketSpringBootApplication {

    public static void main(String[] args) {
        SpringApplication.run(WebSocketSpringBootApplication.class, args);
    }

    @Scheduled(fixedRate = 2000L)
    public void sendMessage() {
        WebSocketHandler.sendMessageToAll("测试webSocket...，当前在线人数：" + WebSocketCache.count());
    }
}
