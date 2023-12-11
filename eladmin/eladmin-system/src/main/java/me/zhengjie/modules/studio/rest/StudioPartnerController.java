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
import me.zhengjie.modules.studio.domain.StudioPartner;
import me.zhengjie.modules.studio.service.StudioPartnerService;
import me.zhengjie.modules.studio.domain.vo.StudioPartnerQueryCriteria;
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
@Api(tags = "工作室合作商管理")
@RequestMapping("/api/studioPartner")
public class StudioPartnerController {

    private final StudioPartnerService studioPartnerService;

    @Log("导出数据")
    @ApiOperation("导出数据")
    @GetMapping(value = "/download")
    @PreAuthorize("@el.check('studioPartner:list')")
    public void exportStudioPartner(HttpServletResponse response, StudioPartnerQueryCriteria criteria) throws IOException {
        studioPartnerService.download(studioPartnerService.queryAll(criteria), response);
    }

    @GetMapping
    @Log("查询工作室合作商")
    @ApiOperation("查询工作室合作商")
    @PreAuthorize("@el.check('studioPartner:list')")
    public ResponseEntity<PageResult<StudioPartner>> queryStudioPartner(StudioPartnerQueryCriteria criteria, Page<Object> page){
        return new ResponseEntity<>(studioPartnerService.queryAll(criteria,page),HttpStatus.OK);
    }

    @PostMapping
    @Log("新增工作室合作商")
    @ApiOperation("新增工作室合作商")
    @PreAuthorize("@el.check('studioPartner:add')")
    public ResponseEntity<Object> createStudioPartner(@Validated @RequestBody StudioPartner resources){
        studioPartnerService.create(resources);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PutMapping
    @Log("修改工作室合作商")
    @ApiOperation("修改工作室合作商")
    @PreAuthorize("@el.check('studioPartner:edit')")
    public ResponseEntity<Object> updateStudioPartner(@Validated @RequestBody StudioPartner resources){
        studioPartnerService.update(resources);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @DeleteMapping
    @Log("删除工作室合作商")
    @ApiOperation("删除工作室合作商")
    @PreAuthorize("@el.check('studioPartner:del')")
    public ResponseEntity<Object> deleteStudioPartner(@RequestBody List<Long> ids) {
        studioPartnerService.deleteAll(ids);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}