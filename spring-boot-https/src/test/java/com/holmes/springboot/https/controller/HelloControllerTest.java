package com.holmes.springboot.https.controller;

import com.alibaba.fastjson.JSONObject;
import com.holmes.springboot.https.BaseTest;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.io.*;

@Slf4j
public class HelloControllerTest extends BaseTest {

    @Test
    public void helloTest() throws Exception {

        MvcResult result = this.getMockMvc()
                .perform(MockMvcRequestBuilders.get("/hello"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.log())
                .andReturn();

        log.info("result: {}", result.getResponse().getContentAsString());
    }

    @Test
    public void getTest() throws Exception {

        MvcResult result = this.getMockMvc()
                .perform(
                        MockMvcRequestBuilders.get("/get")
                                .param("id", "1")
                                .param("name", "holmes")
                                .param("date", "2021-01-01 12:12:12"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.log())
                .andReturn();

        log.info("result: {}", result.getResponse().getContentAsString());
    }

    @Test
    public void postFormTest() throws Exception {

        MvcResult result = this.getMockMvc()
                .perform(
                        MockMvcRequestBuilders.post("/postForm")
                                .param("id", "1")
                                .param("name", "holmes")
                                .param("date", "2021-01-01 12:12:12")
                )
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.log())
                .andReturn();

        log.info("result: {}", result.getResponse().getContentAsString());
    }

    @Test
    public void postForStringTest() throws Exception {

        JSONObject json = new JSONObject();
        json.put("id", "1");
        json.put("name", "holmes");
        json.put("date", "2021-01-01 12:12:12");

        MvcResult result = this.getMockMvc()
                .perform(
                        MockMvcRequestBuilders.post("/postForString")
                                .content(json.toJSONString())
                )
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.log())
                .andReturn();

        log.info("result: {}", result.getResponse().getContentAsString());
    }

    @Test
    public void postForJsonTest() throws Exception {

        JSONObject json = new JSONObject();
        json.put("id", "1");
        json.put("name", "holmes");
        json.put("date", "2021-01-01 12:12:12");

        MvcResult result = this.getMockMvc()
                .perform(
                        MockMvcRequestBuilders.post("/postForJson")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(json.toJSONString())
                )
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.log())
                .andReturn();

        log.info("result: {}", result.getResponse().getContentAsString());
    }

    @Test
    public void postForEntityTest() throws Exception {

        JSONObject json = new JSONObject();
        json.put("id", "1");
        json.put("name", "holmes");
        json.put("date", "2021-01-01 12:12:12");

        MvcResult result = this.getMockMvc()
                .perform(
                        MockMvcRequestBuilders.post("/postForEntity")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(json.toJSONString())
                )
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.log())
                .andReturn();

        log.info("result: {}", result.getResponse().getContentAsString());
    }

    @Test
    public void uploadFileTest() throws Exception {

        FileInputStream fileInputStream = new FileInputStream("/test/test.jpg");
        MockMultipartFile file = new MockMultipartFile("file", "test.jpg", ",multipart/form-data", fileInputStream);
        MvcResult result = this.getMockMvc()
                .perform(
                        MockMvcRequestBuilders.multipart("/uploadFile")
                                .file(file)
                )
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.log())
                .andReturn();

        log.info("result: {}", result.getResponse().getContentAsString());
    }

    @Test
    public void downloadFileTest() throws Exception {

        MvcResult result = this.getMockMvc()
                .perform(
                        MockMvcRequestBuilders.post("/downloadFile")
                )
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.log())
                .andReturn();

        try (FileOutputStream outputStream = new FileOutputStream("/test/" + System.currentTimeMillis() + ".jpg")) {
            outputStream.write(result.getResponse().getContentAsByteArray());
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
