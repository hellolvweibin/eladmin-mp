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
import me.zhengjie.modules.studio.domain.StudioWork;
import me.zhengjie.modules.studio.domain.dto.StudioWorkDTO;
import me.zhengjie.modules.studio.service.StudioWorkService;
import me.zhengjie.modules.studio.domain.vo.StudioWorkQueryCriteria;
import lombok.RequiredArgsConstructor;
import java.util.List;

import me.zhengjie.modules.system.domain.Role;
import me.zhengjie.utils.CacheKey;
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
    @AnonymousAccess
    public void exportStudioWork(HttpServletResponse response, StudioWorkQueryCriteria criteria) throws IOException {
        studioWorkService.download(studioWorkService.queryAll(criteria), response);
    }

//    @GetMapping
//    @Log("查询工作室作品")
//    @ApiOperation("查询工作室作品")
//    @AnonymousAccess
//    public ResponseEntity<PageResult<StudioWork>> queryStudioWork(StudioWorkQueryCriteria criteria, Page<Object> page){
//        return new ResponseEntity<>(studioWorkService.queryAll(criteria, page),HttpStatus.OK);
//    }
    @GetMapping
    @Log("查询工作室作品")
    @ApiOperation("查询工作室作品")
    @AnonymousAccess
    public ResponseEntity<PageResult<StudioWorkDTO>> queryStudioWork(StudioWorkQueryCriteria criteria, Page<Object> page){
        return new ResponseEntity<>(studioWorkService.queryAll(criteria, page),HttpStatus.OK);
    }

    @GetMapping("/getWork/{id}")
    @Log("根据id查询工作室作品")
    @ApiOperation("根据id查询工作室作品")
    @AnonymousAccess
    public ResponseEntity<StudioWork> findStudioWorkById(@PathVariable Long id){

        return new ResponseEntity<>(studioWorkService.findById(id), HttpStatus.OK);
    }

    @GetMapping("/getWorkByNameC")
    @Log("根据中文名查询")
    @ApiOperation("根据中文名查询")
    @AnonymousAccess
    public ResponseEntity<List<StudioWork>> findStudioWorkByNameC(@RequestParam String workNameC){
        List<StudioWork> studioWorks = studioWorkService.findAllByNameC(workNameC);
        return new ResponseEntity<>(studioWorks, HttpStatus.OK);
    }

    @GetMapping("/getWorkByTags")
    @Log("根据标签查询")
    @ApiOperation("根据标签查询")
    @AnonymousAccess
    public ResponseEntity<List<StudioWorkDTO>> findStudioWorkByTags(@RequestParam String workTags, @RequestParam String order){
//        List<StudioWork> studioWorks = studioWorkService.findAllByTags(workTags, page);
        return new ResponseEntity<>(studioWorkService.findAllByTags(workTags, order),HttpStatus.OK);
    }

//    @GetMapping("/getWorkByTags")
//    @Log("根据标签查询")
//    @ApiOperation("根据标签查询")
//    @AnonymousAccess
//    public ResponseEntity<PageResult<StudioWork>> findStudioWorkByTags(@RequestParam String workTags, @RequestParam String order){
////        List<StudioWork> studioWorks = studioWorkService.findAllByTags(workTags, page);
//        return new ResponseEntity<>(studioWorkService.findAllByTags(workTags, order),HttpStatus.OK);
//    }

    @PostMapping
    @Log("新增工作室作品")
    @ApiOperation("新增工作室作品")
    @AnonymousAccess
    public ResponseEntity<Object> createStudioWork(@Validated @RequestBody StudioWork resources){
        studioWorkService.create(resources);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PutMapping
    @Log("修改工作室作品")
    @ApiOperation("修改工作室作品")
    @AnonymousAccess
    public ResponseEntity<Object> updateStudioWork(@Validated @RequestBody StudioWork resources){
        studioWorkService.update(resources);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @DeleteMapping
    @Log("删除工作室作品")
    @ApiOperation("删除工作室作品")
    @AnonymousAccess
    public ResponseEntity<Object> deleteStudioWork(@RequestBody List<Long> ids) {
        studioWorkService.deleteAll(ids);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
