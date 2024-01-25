package me.zhengjie.modules.studio.rest;

import io.swagger.annotations.Api;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Project ：eladmin
 * @Author ：Levi_Bee
 * @Date ：2024/1/25 21:20
 * @description ：
 */
@RestController
@RequiredArgsConstructor
@Api(tags = "图片上传")
@RequestMapping("/api/upload")
public class ImageUploadController {
    //待定
}
