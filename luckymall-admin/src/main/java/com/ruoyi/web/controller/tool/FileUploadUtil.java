package com.ruoyi.web.controller.tool;

import org.apache.commons.io.FilenameUtils;
import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.UUID;

/**
 * @Description: 文件上传工具类
 * @Author: zhenxing.dong
 * @Date: 2020/5/7 23:08
 */
public class FileUploadUtil {

    private static final Logger LOGGER = LoggerFactory.getLogger(FileUploadUtil.class);
    /**
     * 方法说明：文件保存方法
     * @param file  上传的文件
     * @param directory 子目录
     * @return java.lang.String 文件存储地址
     */
    public static String savaFile(MultipartFile file, String directory) {
        String filename = file.getOriginalFilename();
        // 文件后缀名
        String fileSuffixName = FilenameUtils.getExtension(filename);
        String localPath = "/Users/mac/Documents/航天班/毕业设计/LuckyMall/luckymall-admin/src/main/resources/static/image/" + directory + "/";
        File fileDirectory = new File(localPath);
        if (!fileDirectory.exists()) {
            fileDirectory.mkdirs();
        }
        String uuid = UUID.randomUUID().toString().replaceAll("-", "");
        // UUID作为文件名
        filename = uuid + "." + fileSuffixName;
        InputStream inputStream = null;
        FileOutputStream outputStream = null;
        try {
            inputStream = file.getInputStream();
            outputStream = new FileOutputStream(localPath + "/" + filename);
            IOUtils.copy(inputStream, outputStream);
        } catch (IOException e) {
            LOGGER.error("{}",e);
        } finally {
            try {
                inputStream.close();
                outputStream.close();
            } catch (IOException e2) {
                LOGGER.error("{}",e2);
            }
        }
        return "/image/"+directory+"/"+filename;
    }
}