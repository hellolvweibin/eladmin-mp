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
import me.zhengjie.modules.studio.domain.StudioWork;
import me.zhengjie.modules.studio.service.StudioWorkService;
import me.zhengjie.modules.studio.domain.vo.StudioWorkQueryCriteria;
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
@Api(tags = "工作室作品管理")
@RequestMapping("/api/studioWork")
public class StudioWorkController {

    private final StudioWorkService studioWorkService;

    @Log("导出数据")
    @ApiOperation("导出数据")
    @GetMapping(value = "/download")
    @PreAuthorize("@el.check('studioWork:list')")
    public void exportStudioWork(HttpServletResponse response, StudioWorkQueryCriteria criteria) throws IOException {
        studioWorkService.download(studioWorkService.queryAll(criteria), response);
    }

    @GetMapping
    @Log("查询工作室作品")
    @ApiOperation("查询工作室作品")
    @PreAuthorize("@el.check('studioWork:list')")
    public ResponseEntity<PageResult<StudioWork>> queryStudioWork(StudioWorkQueryCriteria criteria, Page<Object> page){
        return new ResponseEntity<>(studioWorkService.queryAll(criteria,page),HttpStatus.OK);
    }

    @PostMapping
    @Log("新增工作室作品")
    @ApiOperation("新增工作室作品")
    @PreAuthorize("@el.check('studioWork:add')")
    public ResponseEntity<Object> createStudioWork(@Validated @RequestBody StudioWork resources){
        studioWorkService.create(resources);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PutMapping
    @Log("修改工作室作品")
    @ApiOperation("修改工作室作品")
    @PreAuthorize("@el.check('studioWork:edit')")
    public ResponseEntity<Object> updateStudioWork(@Validated @RequestBody StudioWork resources){
        studioWorkService.update(resources);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @DeleteMapping
    @Log("删除工作室作品")
    @ApiOperation("删除工作室作品")
    @PreAuthorize("@el.check('studioWork:del')")
    public ResponseEntity<Object> deleteStudioWork(@RequestBody List<Long> ids) {
        studioWorkService.deleteAll(ids);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}