package com.holmes.springboot.fdfs.controller;

import com.holmes.springboot.fdfs.core.FastDFSClient;
import com.holmes.springboot.fdfs.dao.FileInfoDao;
import com.holmes.springboot.fdfs.entity.FileInfo;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;

@RestController
public class UploadController {

    @Autowired
    private FastDFSClient dfsClient;

    @Autowired
    private FileInfoDao fileInfoDao;

    @PostMapping("/upload")
    public String upload(@RequestParam("file") MultipartFile file) {

        try {
            String fileUrl = dfsClient.uploadFile(file);
            FileInfo fileInfo = new FileInfo();
            fileInfo.setFileName(file.getOriginalFilename());
            fileInfo.setUrl(fileUrl);
            fileInfoDao.insert(fileInfo);
            System.out.println("fileUrl: " + fileUrl);
            return "成功上传文件， " + fileUrl;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "error";
    }


    /**
     * 下载文件
     *
     * @param filePath
     * @param request
     * @param response
     * @throws IOException
     */
    @RequestMapping("/download")
    public void download(String filePath, HttpServletRequest request, HttpServletResponse response) throws IOException {

        String[] paths = filePath.split("/");
        String groupName = "";
        for (String item : paths) {
            if (item.contains("group")) {
                groupName = item;
                break;
            }
        }
        String path = filePath.substring(filePath.indexOf(groupName) + groupName.length() + 1, filePath.length());
        InputStream input = dfsClient.download(groupName, path);

        //根据文件名获取 MIME 类型
        String fileName = paths[paths.length - 1];
        System.out.println("fileName :" + fileName);
        String contentType = request.getServletContext().getMimeType(fileName);
        String contentDisposition = "attachment;filename=" + fileName;

        // 设置头
        response.setHeader("Content-Type", contentType);
        response.setHeader("Content-Disposition", contentDisposition);

        // 获取绑定了客户端的流
        ServletOutputStream output = response.getOutputStream();

        // 把输入流中的数据写入到输出流中
        IOUtils.copy(input, output);
        input.close();
    }

    /**
     * 删除文件
     *
     * @param filePath
     * @return
     */
    @RequestMapping("/deleteFile")
    public String delFile(String filePath) {

        try {
            String[] paths = filePath.split("/");
            String groupName = "";
            for (String item : paths) {
                if (item.contains("group")) {
                    groupName = item;
                    break;
                }
            }
            String path = filePath.substring(filePath.indexOf(groupName) + groupName.length() + 1, filePath.length());
            dfsClient.delFile(groupName, path);
            return "成功删除，'" + filePath;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "error";
    }
}