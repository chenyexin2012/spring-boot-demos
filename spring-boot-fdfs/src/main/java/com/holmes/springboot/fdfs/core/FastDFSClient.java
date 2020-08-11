package com.holmes.springboot.fdfs.core;

import com.github.tobato.fastdfs.domain.fdfs.StorePath;
import com.github.tobato.fastdfs.domain.fdfs.ThumbImageConfig;
import com.github.tobato.fastdfs.domain.proto.storage.DownloadCallback;
import com.github.tobato.fastdfs.service.FastFileStorageClient;
import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;

@Component
public class FastDFSClient {

    @Value("${fdfs.nginxHost}")
    private String nginxHost;

    @Value("${fdfs.nginxPort}")
    private String nginxPort;

    @Autowired
    private FastFileStorageClient storageClient;

    @Autowired
    private ThumbImageConfig thumbImageConfig; //创建缩略图   ， 缩略图访问有问题，暂未解决

    /**
     * 上传文件
     *
     * @param file
     * @return
     * @throws IOException
     */
    public String uploadFile(MultipartFile file) throws IOException {

        StorePath storePath = storageClient.uploadFile(file.getInputStream(), file.getSize(),
                FilenameUtils.getExtension(file.getOriginalFilename()), null);

        System.out.println("storePath: " + storePath);
        String path = thumbImageConfig.getThumbImagePath(storePath.getPath());
        System.out.println("thumbImage: " + path);  //   缩略图访问有问题，暂未解决

        return getResAccessUrl(storePath);
    }

    /**
     * 上传文件
     *
     * @param file
     * @return
     * @throws FileNotFoundException
     */
    public String uploadFile(File file) throws FileNotFoundException {

        StorePath storePath = storageClient.uploadFile(new FileInputStream(file), file.length(), file.getName(), null);

        System.out.println("storePath: " + storePath);
        String path = thumbImageConfig.getThumbImagePath(storePath.getPath());
        System.out.println("thumbImage: " + path);  //   缩略图访问有问题，暂未解决

        return getResAccessUrl(storePath);
    }

    /**
     * 删除文件
     *
     * @param groupName
     * @param path
     */
    public void delFile(String groupName, String path) {
        storageClient.deleteFile(groupName, path);

    }

    /**
     * 下载文件
     *
     * @param groupName
     * @param path
     * @return
     */
    public InputStream download(String groupName, String path) {
        return storageClient.downloadFile(groupName, path,  new DownloadCallback<InputStream>() {
            @Override
            public InputStream recv(InputStream ins) throws IOException {
                return ins;
            }
        });
    }

    /**
     * 通过存储路径返回文件的访问地址
     *
     * @param storePath
     * @return
     */
    private String getResAccessUrl(StorePath storePath) {
        return "http://" + nginxHost + ":" + nginxPort + "/" + storePath.getFullPath();
    }
}
