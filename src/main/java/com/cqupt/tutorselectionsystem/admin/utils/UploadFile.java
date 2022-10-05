package com.cqupt.tutorselectionsystem.admin.utils;

import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.util.UUID;

public class UploadFile {
    public static String uploadToFolder(HttpServletRequest request, String path, MultipartFile file) {
        //获得上传地址
        //String path1 = request.getSession().getServletContext().getRealPath(path);
        //判断路径是否存在，如果不存在则建立该路径
        File file1 = new File(path);
        if (!file1.exists()) {
            file1.mkdirs();
        }
        //获得上传的文件名，为了防止出现中文乱码问题，这里不使用原来的图片名作为新名称的一部分
        String fileName = file.getOriginalFilename();
        try {
            file.transferTo(new File(path, fileName));
        } catch (IOException e) {
            e.printStackTrace();
        }
        //return fileURL;//返回的url
        return fileName;
    }
}
