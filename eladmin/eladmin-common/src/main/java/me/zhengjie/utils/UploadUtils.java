package me.zhengjie.utils;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.io.PrintStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

/**
 * 上传图片工具类
 * @author levi_bee
 */
public class UploadUtils {
    private static final String IMG_PATH = "/Users/levi_bee/Desktop/Up/MySideline/eladmin-mp/image";

    public static String uploadImage(MultipartFile multipartFile){

        try {
            String realFileName = multipartFile.getOriginalFilename();

            String imgSuffix = realFileName.substring(realFileName.lastIndexOf("."));

            String newFilename = UUID.randomUUID() +imgSuffix;

            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
            String datePath = dateFormat.format(new Date());
            //上传到哪个路径下
            File targetPath = new File(IMG_PATH+ newFilename, datePath);
            if (!targetPath.exists()){
                targetPath.mkdirs();
            }
            File targetFilename = new File(targetPath, newFilename);
            multipartFile.transferTo(targetFilename);
//            return String.valueOf(targetFilename);//返回文件路径
            /**
             * 返回文件路径
             * 返回给前端
             */
            return "upload/"+datePath+"/"+newFilename;
        } catch (IOException e) {
            e.printStackTrace();
            return "失败！";
        }
    }

}
