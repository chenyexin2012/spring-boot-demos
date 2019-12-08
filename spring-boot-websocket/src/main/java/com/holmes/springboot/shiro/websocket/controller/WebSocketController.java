package com.holmes.springboot.shiro.websocket.controller;

import com.holmes.springboot.websocket.socket.WebSocketHandler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/websocket")
@Slf4j
public class WebSocketController {

    @GetMapping("/socket/{id}")
    public ModelAndView socket(@PathVariable String id) {
        log.info("cid = {}", id);
        ModelAndView mav = new ModelAndView("websocket");
        mav.addObject("id", id);
        return mav;
    }

    /**
     * 推送数据
     *
     * @param id
     * @param message
     * @return
     */
    @ResponseBody
    @RequestMapping("/socket/push/{id}")
    public String sendMessage(@PathVariable String id, String message) {
        log.info("{} send message: {}", message);
        WebSocketHandler.sendMessage(id, message);
        return "success";
    }
}
