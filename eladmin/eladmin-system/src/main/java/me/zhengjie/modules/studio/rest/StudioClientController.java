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
import me.zhengjie.modules.studio.domain.StudioClient;
import me.zhengjie.modules.studio.service.StudioClientService;
import me.zhengjie.modules.studio.domain.vo.StudioClientQueryCriteria;
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
@Api(tags = "工作室客户管理")
@RequestMapping("/api/studioClient")
public class StudioClientController {

    private final StudioClientService studioClientService;

    @Log("导出数据")
    @ApiOperation("导出数据")
    @GetMapping(value = "/download")
    @PreAuthorize("@el.check('studioClient:list')")
    public void exportStudioClient(HttpServletResponse response, StudioClientQueryCriteria criteria) throws IOException {
        studioClientService.download(studioClientService.queryAll(criteria), response);
    }

    @GetMapping
    @Log("查询工作室客户")
    @ApiOperation("查询工作室客户")
    @PreAuthorize("@el.check('studioClient:list')")
    public ResponseEntity<PageResult<StudioClient>> queryStudioClient(StudioClientQueryCriteria criteria, Page<Object> page){
        return new ResponseEntity<>(studioClientService.queryAll(criteria,page),HttpStatus.OK);
    }

    @PostMapping
    @Log("新增工作室客户")
    @ApiOperation("新增工作室客户")
    @PreAuthorize("@el.check('studioClient:add')")
    public ResponseEntity<Object> createStudioClient(@Validated @RequestBody StudioClient resources){
        studioClientService.create(resources);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PutMapping
    @Log("修改工作室客户")
    @ApiOperation("修改工作室客户")
    @PreAuthorize("@el.check('studioClient:edit')")
    public ResponseEntity<Object> updateStudioClient(@Validated @RequestBody StudioClient resources){
        studioClientService.update(resources);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @DeleteMapping
    @Log("删除工作室客户")
    @ApiOperation("删除工作室客户")
    @PreAuthorize("@el.check('studioClient:del')")
    public ResponseEntity<Object> deleteStudioClient(@RequestBody List<Long> ids) {
        studioClientService.deleteAll(ids);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}