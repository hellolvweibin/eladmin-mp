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

import me.zhengjie.modules.studio.domain.StudioWorkStaff;
import me.zhengjie.utils.FileUtil;
import lombok.RequiredArgsConstructor;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import me.zhengjie.modules.studio.service.StudioWorkStaffService;
import me.zhengjie.modules.studio.domain.vo.StudioWorkStaffQueryCriteria;
import me.zhengjie.modules.studio.mapper.StudioWorkStaffMapper;
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
public class StudioWorkStaffServiceImpl extends ServiceImpl<StudioWorkStaffMapper, StudioWorkStaff> implements StudioWorkStaffService {

    private final StudioWorkStaffMapper studioWorkStaffMapper;

    @Override
    public PageResult<StudioWorkStaff> queryAll(StudioWorkStaffQueryCriteria criteria, Page<Object> page){
        return PageUtil.toPage(studioWorkStaffMapper.findAll(criteria, page));
    }

    @Override
    public List<StudioWorkStaff> queryAll(StudioWorkStaffQueryCriteria criteria){
        return studioWorkStaffMapper.findAll(criteria);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void create(StudioWorkStaff resources) {
        save(resources);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void update(StudioWorkStaff resources) {
        StudioWorkStaff studioWorkStaff = getById(resources.getId());
        studioWorkStaff.copy(resources);
        saveOrUpdate(studioWorkStaff);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteAll(List<Long> ids) {
        removeBatchByIds(ids);
    }

    @Override
    public void download(List<StudioWorkStaff> all, HttpServletResponse response) throws IOException {
        List<Map<String, Object>> list = new ArrayList<>();
        for (StudioWorkStaff studioWorkStaff : all) {
            Map<String,Object> map = new LinkedHashMap<>();
            map.put("作品id", studioWorkStaff.getWorkId());
            map.put("成员id", studioWorkStaff.getStaffId());
            map.put("成员tag", studioWorkStaff.getStaffTag());
            map.put("成员英文名", studioWorkStaff.getStaffName());
            map.put("成员中文名", studioWorkStaff.getStaffNamec());
            list.add(map);
        }
        FileUtil.downloadExcel(list, response);
    }
}