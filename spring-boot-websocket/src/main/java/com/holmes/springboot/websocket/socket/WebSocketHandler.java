package com.holmes.springboot.websocket.socket;

import lombok.extern.slf4j.Slf4j;

import javax.websocket.Session;
import java.io.IOException;
import java.util.Collection;

@Slf4j
public class WebSocketHandler {

    public static void sendMessage(String id, String message) {
        Session session = WebSocketCache.getSession(id);
        if(session != null) {
            try {
                session.getBasicRemote().sendText(message);
            } catch (IOException e) {
                log.error("发送消息出现异常", e);
            }
        }
    }

    public static void sendMessageToAll(String message) {
        Collection<Session> sessions = WebSocketCache.getAllSessions();
        for(Session session : sessions) {
            try {
                session.getBasicRemote().sendText(message);
            } catch (IOException e) {
                log.error("发送消息出现异常", e);
            }
        }
    }
}
