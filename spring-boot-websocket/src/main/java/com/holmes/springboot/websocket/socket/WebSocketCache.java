package com.holmes.springboot.websocket.socket;

import javax.websocket.Session;
import java.util.Collection;
import java.util.concurrent.ConcurrentHashMap;

public class WebSocketCache {

    /**
     *
     */
    private final static ConcurrentHashMap<String, Session> WEB_SOCKET_CACHE =
            new ConcurrentHashMap<>();

    public static void add(String id, Session session) {
        WEB_SOCKET_CACHE.put(id, session);
    }

    public static Session getSession(String id) {
        return WEB_SOCKET_CACHE.get(id);
    }

    public static void remove(String id) {
        WEB_SOCKET_CACHE.remove(id);
    }

    public static int count() {
        return WEB_SOCKET_CACHE.size();
    }

    public static Collection<Session> getAllSessions() {
        return WEB_SOCKET_CACHE.values();
    }
}
