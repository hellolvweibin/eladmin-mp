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

import me.zhengjie.modules.studio.domain.StudioBase;
import me.zhengjie.utils.FileUtil;
import lombok.RequiredArgsConstructor;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import me.zhengjie.modules.studio.service.StudioBaseService;
import me.zhengjie.modules.studio.domain.vo.StudioBaseQueryCriteria;
import me.zhengjie.modules.studio.mapper.StudioBaseMapper;
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
public class StudioBaseServiceImpl extends ServiceImpl<StudioBaseMapper, StudioBase> implements StudioBaseService {

    private final StudioBaseMapper studioBaseMapper;

    @Override
    public PageResult<StudioBase> queryAll(StudioBaseQueryCriteria criteria, Page<Object> page){
        return PageUtil.toPage(studioBaseMapper.findAll(criteria, page));
    }

    @Override
    public List<StudioBase> queryAll(StudioBaseQueryCriteria criteria){
        return studioBaseMapper.findAll(criteria);
    }

    @Override
    public List<StudioBase> selectAll() {
        return studioBaseMapper.selectAll();
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void create(StudioBase resources) {
        save(resources);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void update(StudioBase resources) {
        StudioBase studioBase = getById(resources.getStudioId());
        studioBase.copy(resources);
        saveOrUpdate(studioBase);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteAll(List<Long> ids) {
        removeBatchByIds(ids);
    }

    @Override
    public void download(List<StudioBase> all, HttpServletResponse response) throws IOException {
        List<Map<String, Object>> list = new ArrayList<>();
        for (StudioBase studioBase : all) {
            Map<String,Object> map = new LinkedHashMap<>();
            map.put("工作室英文名", studioBase.getStudioName());
            map.put("工作室中文名", studioBase.getStudioNameC());
            map.put("工作室英文描述", studioBase.getStudioDes());
            map.put("工作室中文描述", studioBase.getStudioDesC());
            map.put("工作室英文地址", studioBase.getStudioLoc());
            map.put("工作室中文地址", studioBase.getStudioLocC());
            map.put("工作室经度", studioBase.getStudioLocLon());
            map.put("工作室纬度", studioBase.getStudioLocLat());
            map.put("工作室邮箱", studioBase.getStudioMail());
            map.put("工作室ig", studioBase.getStudioIg());
            map.put("工作室小红书", studioBase.getStudioRed());
            list.add(map);
        }
        FileUtil.downloadExcel(list, response);
    }
}
