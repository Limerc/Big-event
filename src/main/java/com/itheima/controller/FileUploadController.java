package com.itheima.controller;

import com.itheima.pojo.Result;
import com.itheima.utils.AliOssUtil;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

@RestController
public class FileUploadController {
    @PostMapping("/upload")
    public Result<String> upload(MultipartFile file) throws Exception {
        // 把文件的内容存储到本地磁盘上
        String originalFilename = file.getOriginalFilename();  // 定义文件名
        String fileName = UUID.randomUUID().toString() + originalFilename.substring(originalFilename.lastIndexOf("."));
        //file.transferTo(new File("D:\\Spring_items\\Big_events\\files\\" + fileName));
        String url = AliOssUtil.uploadFile(fileName, file.getInputStream());
        return Result.success(url);
    }
}
