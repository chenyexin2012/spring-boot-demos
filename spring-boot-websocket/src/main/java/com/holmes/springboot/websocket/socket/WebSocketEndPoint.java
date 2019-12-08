package com.holmes.springboot.websocket.socket;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.websocket.*;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;

@ServerEndpoint("/websocket/{id}")
@Component
@Slf4j
public class WebSocketEndPoint {

    /**
     * 连接建立成功调用的方法
     */
    @OnOpen
    public void onOpen(Session session, @PathParam("id") String id) {

        log.info("有新的连接，session: {}, id = {}", session, id);
        WebSocketCache.add(id, session);
    }

    /**
     * 连接关闭调用的方法
     */
    @OnClose
    public void onClose(@PathParam("id") String id) {
        log.info("连接关闭，id: {}", id);
        WebSocketCache.remove(id);
    }

    /**
     * 收到客户端消息后调用的方法
     *
     * @param session
     * @param message
     */
    @OnMessage
    public void onMessage(Session session, String message) {
        log.info("收到来自{}的信息: {}", session, message);
    }

    /**
     * @param session
     * @param error
     */
    @OnError
    public void onError(Session session, Throwable error) {
        try {
            session.close();
        } catch (IOException e) {
            log.error("发生错误", e);
        }
        log.error("连接出现异常", error);
    }
}
