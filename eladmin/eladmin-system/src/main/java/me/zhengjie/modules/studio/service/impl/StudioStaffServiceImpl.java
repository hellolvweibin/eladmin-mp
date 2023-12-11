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
package me.zhengjie.modules.studio.service.impl;

import me.zhengjie.modules.studio.domain.StudioStaff;
import me.zhengjie.utils.FileUtil;
import lombok.RequiredArgsConstructor;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import me.zhengjie.modules.studio.service.StudioStaffService;
import me.zhengjie.modules.studio.domain.vo.StudioStaffQueryCriteria;
import me.zhengjie.modules.studio.mapper.StudioStaffMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import me.zhengjie.utils.PageUtil;
import java.util.List;
import java.util.Map;
import java.io.IOException;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import me.zhengjie.utils.PageResult;

/**
* @description 服务实现
* @author lv
* @date 2023-12-11
**/
@Service
@RequiredArgsConstructor
public class StudioStaffServiceImpl extends ServiceImpl<StudioStaffMapper, StudioStaff> implements StudioStaffService {

    private final StudioStaffMapper studioStaffMapper;

    @Override
    public PageResult<StudioStaff> queryAll(StudioStaffQueryCriteria criteria, Page<Object> page){
        return PageUtil.toPage(studioStaffMapper.findAll(criteria, page));
    }

    @Override
    public List<StudioStaff> queryAll(StudioStaffQueryCriteria criteria){
        return studioStaffMapper.findAll(criteria);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void create(StudioStaff resources) {
        save(resources);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void update(StudioStaff resources) {
        StudioStaff studioStaff = getById(resources.getStaffId());
        studioStaff.copy(resources);
        saveOrUpdate(studioStaff);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteAll(List<Long> ids) {
        removeBatchByIds(ids);
    }

    @Override
    public void download(List<StudioStaff> all, HttpServletResponse response) throws IOException {
        List<Map<String, Object>> list = new ArrayList<>();
        for (StudioStaff studioStaff : all) {
            Map<String,Object> map = new LinkedHashMap<>();
            map.put("员工英文名", studioStaff.getStaffName());
            map.put("员工中文名", studioStaff.getStaffNameC());
            map.put("员工性别（0、未知，1、男，2、女）", studioStaff.getStaffSex());
            map.put("员工工作室id", studioStaff.getStaffStudioId());
            map.put("创建日期", studioStaff.getCreateTime());
            map.put("更新时间", studioStaff.getUpdateTime());
            list.add(map);
        }
        FileUtil.downloadExcel(list, response);
    }
}
