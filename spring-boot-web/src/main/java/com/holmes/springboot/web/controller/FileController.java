package com.holmes.springboot.web.controller;

import com.holmes.springboot.web.model.ResponseData;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.*;

@Slf4j
@RestController
@RequestMapping("/file")
public class FileController {

    private static final String FILE_PATH = "/var/file/";

    /**
     * 测试文件上传
     *
     * @param file
     * @return
     */
    @PostMapping("/upload")
    public ResponseData uploadFile(@RequestParam("file") MultipartFile file) {

        log.info("name: {}", file.getName());
        try (FileOutputStream fileOutputStream = new FileOutputStream("/test/out.jpg")) {
            fileOutputStream.write(file.getBytes());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return new ResponseData().success();
    }

    @PostMapping("/download")
    public void downloadFile(@RequestParam("fileName") String fileName, HttpServletResponse response) {

        // 二进制流
        response.setContentType("application/octet-stream");
        // 指定默认文件名
        response.addHeader("Content-Disposition", "attachment;filename=" + fileName);
        try (FileInputStream fileInputStream = new FileInputStream(FILE_PATH + fileName);
             OutputStream outputStream = response.getOutputStream()) {
            byte[] buffer = new byte[1024 * 1024];
            int n;
            while ((n = fileInputStream.read(buffer)) >= 0) {
                outputStream.write(buffer, 0, n);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
