package com.holmes.springboot.web.controller;

import com.alibaba.fastjson.JSONObject;
import com.holmes.springboot.web.model.ResponseData;
import com.holmes.springboot.web.model.request.PostForEntityRequest;
import com.holmes.springboot.web.model.vo.PostForEntityVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.util.Date;

@Slf4j
@RestController
@RequestMapping("/test")
public class TestController {

    @GetMapping(value = "hello")
    public ResponseData hello() {
        return new ResponseData().success();
    }

    /**
     * 接收URL参数，也可以使用@RequestParam注解指定请求参数名，由于该注解的required属性默认为true，所以默认必须传入该参数，
     * 否则会提示 “Resolved [org.springframework.web.bind.MissingServletRequestParameterException: Required String parameter 'name' is not present”，
     * 也可以通过defaultValue指定一个默认值，此时required属性会失效。@RequestParam注解也可以用于表单请求的参数。
     * <p>
     * -@DateTimeFormat注解用于Date类型的转换。
     *
     * @param id
     * @param name
     * @param date
     * @return
     */
    @GetMapping("get")
    public ResponseData get(Long id, @RequestParam(value = "name", defaultValue = "sherlock") String name,
                            @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") Date date) {

        log.info("id: {}, name: {}, date: {}", id, name, date);
        return new ResponseData().success();
    }

    /**
     * 接收表单请求，也可以用来接收URL参数
     * <p>
     * -@DateTimeFormat无法与@RequestBody一同使用，只能作用与表单上，在postForEntity方法中，dto里使用的注解为@JsonFormat
     *
     * @param id
     * @param name
     * @param date
     * @return
     */
    @PostMapping("postForm")
    public ResponseData postForm(Long id, @RequestParam(value = "name", defaultValue = "sherlock") String name,
                                 @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") Date date) {

        log.info("id: {}, name: {}, date: {}", id, name, date);
        return new ResponseData().success();
    }

    /**
     * 接收 String 类型的数据
     *
     * @param data
     * @return
     */
    @PostMapping("postForString")
    public String postForString(@RequestBody String data) {

        log.info("data: {}", data);
        return data;
    }

    /**
     * 接收 JSON 类型数据
     *
     * @param data
     * @return
     */
    @PostMapping("postForJson")
    public JSONObject postForJson(@RequestBody JSONObject data) {

        log.info("data: {}", data);

        JSONObject result = new JSONObject();
        result.put("result", "OK");
        return result;
    }

    /**
     * 使用实体类接收参数
     *
     * @param data
     * @return
     */
    @PostMapping("postForEntity")
    public ResponseData postForEntity(@RequestBody PostForEntityRequest data) {

        log.info("data: {}", data);
        PostForEntityVo vo = new PostForEntityVo();
        vo.setId(data.getId() + 1);
        vo.setName(data.getName());
        vo.setDate(new Date());
        return new ResponseData().success(vo);
    }
}
