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
import me.zhengjie.modules.studio.domain.StudioCarousel;
import me.zhengjie.modules.studio.service.StudioCarouselService;
import me.zhengjie.modules.studio.domain.vo.StudioCarouselQueryCriteria;
import lombok.RequiredArgsConstructor;

import java.awt.image.BufferedImage;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import io.swagger.annotations.*;
import java.io.IOException;
import java.util.UUID;
import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletResponse;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import me.zhengjie.utils.PageResult;
import org.springframework.web.multipart.MultipartFile;

/**
* @author lv
* @date 2023-12-11
**/
@RestController
@RequiredArgsConstructor
@Api(tags = "工作室轮播图管理")
@RequestMapping("/api/studioCarousel")
public class StudioCarouselController {

    private final StudioCarouselService studioCarouselService;

    @Log("导出数据")
    @ApiOperation("导出数据")
    @GetMapping(value = "/download")
    @AnonymousAccess
    public void exportStudioCarousel(HttpServletResponse response, StudioCarouselQueryCriteria criteria) throws IOException {
        studioCarouselService.download(studioCarouselService.queryAll(criteria), response);
    }

    @GetMapping
    @Log("查询工作室轮播图")
    @ApiOperation("查询工作室轮播图")
    @AnonymousAccess
    public ResponseEntity<PageResult<StudioCarousel>> queryStudioCarousel(StudioCarouselQueryCriteria criteria, Page<Object> page){
        return new ResponseEntity<>(studioCarouselService.queryAll(criteria,page),HttpStatus.OK);
    }

    @PostMapping
    @Log("新增工作室轮播图")
    @ApiOperation("新增工作室轮播图")
    @AnonymousAccess
    public ResponseEntity<Object> createStudioCarousel(@Validated @RequestBody StudioCarousel resources){
        studioCarouselService.create(resources);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PutMapping
    @Log("修改工作室轮播图")
    @ApiOperation("修改工作室轮播图")
    @AnonymousAccess
    public ResponseEntity<Object> updateStudioCarousel(@Validated @RequestBody StudioCarousel resources){
        studioCarouselService.update(resources);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @DeleteMapping
    @Log("删除工作室轮播图")
    @ApiOperation("删除工作室轮播图")
    @AnonymousAccess
    public ResponseEntity<Object> deleteStudioCarousel(@RequestBody List<Integer> ids) {
        studioCarouselService.deleteAll(ids);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
