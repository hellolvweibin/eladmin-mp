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
import me.zhengjie.modules.studio.domain.StudioBase;
import me.zhengjie.modules.studio.service.StudioBaseService;
import me.zhengjie.modules.studio.domain.vo.StudioBaseQueryCriteria;
import lombok.RequiredArgsConstructor;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import io.swagger.annotations.*;
import java.io.IOException;
import javax.servlet.http.HttpServletResponse;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import me.zhengjie.utils.PageResult;

/**
* @author lv
* @date 2023-12-11
**/
@RestController
@RequiredArgsConstructor
@Api(tags = "工作室基本信息管理")
@RequestMapping("/api/studioBase")
public class StudioBaseController {

    private final StudioBaseService studioBaseService;

    @Log("导出数据")
    @ApiOperation("导出数据")
    @GetMapping(value = "/download")
    @AnonymousAccess
    public void exportStudioBase(HttpServletResponse response, StudioBaseQueryCriteria criteria) throws IOException {
        studioBaseService.download(studioBaseService.queryAll(criteria), response);
    }

    @GetMapping
    @Log("查询工作室基本信息")
    @ApiOperation("查询工作室基本信息")
    @AnonymousAccess
    public ResponseEntity<PageResult<StudioBase>> queryStudioBase(StudioBaseQueryCriteria criteria, Page<Object> page) {
        return new ResponseEntity<>(studioBaseService.queryAll(criteria, page), HttpStatus.OK);
    }

    @PostMapping
    @Log("新增工作室基本信息")
    @ApiOperation("新增工作室基本信息")
    @AnonymousAccess
    public ResponseEntity<Object> createStudioBase(@Validated @RequestBody StudioBase resources){
        studioBaseService.create(resources);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PutMapping
    @Log("修改工作室基本信息")
    @ApiOperation("修改工作室基本信息")
    @AnonymousAccess
    public ResponseEntity<Object> updateStudioBase(@Validated @RequestBody StudioBase resources){
        studioBaseService.update(resources);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @DeleteMapping
    @Log("删除工作室基本信息")
    @ApiOperation("删除工作室基本信息")
    @AnonymousAccess
    public ResponseEntity<Object> deleteStudioBase(@RequestBody List<Long> ids) {
        studioBaseService.deleteAll(ids);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
