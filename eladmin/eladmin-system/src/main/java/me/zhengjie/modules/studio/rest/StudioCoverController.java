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

import me.zhengjie.annotation.Log;
import me.zhengjie.modules.studio.domain.StudioCover;
import me.zhengjie.modules.studio.service.StudioCoverService;
import me.zhengjie.modules.studio.domain.vo.StudioCoverQueryCriteria;
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
@Api(tags = "工作室轮播图管理")
@RequestMapping("/api/studioCover")
public class StudioCoverController {

    private final StudioCoverService studioCoverService;

    @Log("导出数据")
    @ApiOperation("导出数据")
    @GetMapping(value = "/download")
    @PreAuthorize("@el.check('studioCover:list')")
    public void exportStudioCover(HttpServletResponse response, StudioCoverQueryCriteria criteria) throws IOException {
        studioCoverService.download(studioCoverService.queryAll(criteria), response);
    }

    @GetMapping
    @Log("查询工作室轮播图")
    @ApiOperation("查询工作室轮播图")
    @PreAuthorize("@el.check('studioCover:list')")
    public ResponseEntity<PageResult<StudioCover>> queryStudioCover(StudioCoverQueryCriteria criteria, Page<Object> page){
        return new ResponseEntity<>(studioCoverService.queryAll(criteria,page),HttpStatus.OK);
    }

    @PostMapping
    @Log("新增工作室轮播图")
    @ApiOperation("新增工作室轮播图")
    @PreAuthorize("@el.check('studioCover:add')")
    public ResponseEntity<Object> createStudioCover(@Validated @RequestBody StudioCover resources){
        studioCoverService.create(resources);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PutMapping
    @Log("修改工作室轮播图")
    @ApiOperation("修改工作室轮播图")
    @PreAuthorize("@el.check('studioCover:edit')")
    public ResponseEntity<Object> updateStudioCover(@Validated @RequestBody StudioCover resources){
        studioCoverService.update(resources);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @DeleteMapping
    @Log("删除工作室轮播图")
    @ApiOperation("删除工作室轮播图")
    @PreAuthorize("@el.check('studioCover:del')")
    public ResponseEntity<Object> deleteStudioCover(@RequestBody List<Integer> ids) {
        studioCoverService.deleteAll(ids);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}