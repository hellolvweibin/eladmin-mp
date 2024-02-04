/*
 *  Copyright 2019-2023 Zheng Jie
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *  http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */
package me.zhengjie.modules.studio.rest;

import me.zhengjie.annotation.AnonymousAccess;
import me.zhengjie.annotation.Log;
import me.zhengjie.modules.studio.domain.Image;
import me.zhengjie.modules.studio.domain.Result;
import me.zhengjie.modules.studio.domain.StudioImage;
import me.zhengjie.modules.studio.service.StudioImageService;
import me.zhengjie.modules.studio.domain.vo.StudioImageQueryCriteria;
import lombok.RequiredArgsConstructor;

import java.awt.image.BufferedImage;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import io.swagger.annotations.*;

import java.io.IOException;
import java.util.UUID;
import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import me.zhengjie.utils.PageResult;
import org.springframework.web.multipart.MultipartFile;

/**
 * @author lv
 * @date 2024-01-28
 **/
@RestController
@RequiredArgsConstructor
@Api(tags = "图片上传管理")
@RequestMapping("/api/studioImage")
public class StudioImageController {
    private static final String BASE_URL = "/Users/levi_bee/Desktop/Up/MySideline/eladmin-mp/eladmin-web/src/assets/images/";

    private final StudioImageService studioImageService;

    @Log("上传图片")
    @ApiOperation("上传图片")
    @PostMapping(value = "/upload")
    @AnonymousAccess
    public Result<Image> upload(@RequestParam("file") MultipartFile file) {
        if (file.isEmpty()) {
            Result<Image> ans = new Result<>();
            ans.setCode(500);
            ans.setMessage("请上传文件");
            return ans;
        }

        try {
            // 获取文件名
            String originalFilename = file.getOriginalFilename();
            // 生成当前日期字符串
            String currentDate = new SimpleDateFormat("yyyyMMdd").format(new Date());

            // 生成一个唯一的6位ID
            String uniqueId = UUID.randomUUID().toString().substring(0, 6);

            // 生成新的文件名
            String fileName = currentDate + "_" + uniqueId + "_" + originalFilename;
            // 保存文件到指定路径
            String filePath = BASE_URL + File.separator + fileName;
            file.transferTo(new File(filePath));

            // 返回文件的访问地址
            String fileUrl = BASE_URL + fileName;

            File imageFile = new File(fileUrl);
            BufferedImage bufferedImage = ImageIO.read(imageFile);
            int height = 0;
            int width = 0;
            if (bufferedImage != null) {
                width = bufferedImage.getWidth();
                height = bufferedImage.getHeight();

                System.out.println("Image Width: " + width);
                System.out.println("Image Height: " + height);
            } else {
                System.out.println("Failed to read the image.");
            }
            Image image = new Image();
            image.setPath(fileName);
            image.setHeight(height);
            image.setWidth(width);
            Result<Image> studioImageResult = new Result<>();
            studioImageResult.setCode(200);
            studioImageResult.setMessage("文件上传成功");
            studioImageResult.setData(image);
            return studioImageResult;
        } catch (IOException e) {
            e.printStackTrace();
            Result<Image> fail = new Result<>();
            fail.setCode(500);
            fail.setMessage("文件上传失败");
            return fail;
        }
    }

    @Log("导出数据")
    @ApiOperation("导出数据")
    @GetMapping(value = "/download")
    @PreAuthorize("@el.check('studioImage:list')")
    public void exportStudioImage(HttpServletResponse response, StudioImageQueryCriteria criteria) throws IOException {
        studioImageService.download(studioImageService.queryAll(criteria), response);
    }

    @GetMapping
    @Log("查询图片上传")
    @ApiOperation("查询图片上传")
    @PreAuthorize("@el.check('studioImage:list')")
    public ResponseEntity<PageResult<StudioImage>> queryStudioImage(StudioImageQueryCriteria criteria, Page<Object> page) {
        return new ResponseEntity<>(studioImageService.queryAll(criteria, page), HttpStatus.OK);
    }

    @PostMapping
    @Log("新增图片上传")
    @ApiOperation("新增图片上传")
    @PreAuthorize("@el.check('studioImage:add')")
    public ResponseEntity<Object> createStudioImage(@Validated @RequestBody StudioImage resources) {
        studioImageService.create(resources);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PutMapping
    @Log("修改图片上传")
    @ApiOperation("修改图片上传")
    @PreAuthorize("@el.check('studioImage:edit')")
    public ResponseEntity<Object> updateStudioImage(@Validated @RequestBody StudioImage resources) {
        studioImageService.update(resources);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @DeleteMapping
    @Log("删除图片上传")
    @ApiOperation("删除图片上传")
    @PreAuthorize("@el.check('studioImage:del')")
    public ResponseEntity<Object> deleteStudioImage(@RequestBody List<Long> ids) {
        studioImageService.deleteAll(ids);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
