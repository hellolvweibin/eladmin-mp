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

import me.zhengjie.modules.studio.domain.StudioWork;
import me.zhengjie.utils.FileUtil;
import lombok.RequiredArgsConstructor;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import me.zhengjie.modules.studio.service.StudioWorkService;
import me.zhengjie.modules.studio.domain.vo.StudioWorkQueryCriteria;
import me.zhengjie.modules.studio.mapper.StudioWorkMapper;
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
public class StudioWorkServiceImpl extends ServiceImpl<StudioWorkMapper, StudioWork> implements StudioWorkService {

    private final StudioWorkMapper studioWorkMapper;

    @Override
    public StudioWork findById(Long id) {
        return getById(id);
    }

    @Override
    public PageResult<StudioWork> queryAll(StudioWorkQueryCriteria criteria, Page<Object> page){
        return PageUtil.toPage(studioWorkMapper.findAll(criteria, page));
    }

    @Override
    public List<StudioWork> queryAll(StudioWorkQueryCriteria criteria){
        return studioWorkMapper.findAll(criteria);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void create(StudioWork resources) {
        save(resources);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void update(StudioWork resources) {
        StudioWork studioWork = getById(resources.getWorkId());
        studioWork.copy(resources);
        saveOrUpdate(studioWork);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteAll(List<Long> ids) {
        removeBatchByIds(ids);
    }

    @Override
    public void download(List<StudioWork> all, HttpServletResponse response) throws IOException {
        List<Map<String, Object>> list = new ArrayList<>();
        for (StudioWork studioWork : all) {
            Map<String,Object> map = new LinkedHashMap<>();
            map.put("作品英文名", studioWork.getWorkName());
            map.put("作品中文名", studioWork.getWorkNameC());
            map.put("作品类别", studioWork.getWorkType());
            map.put("作品英文描述", studioWork.getWorkDes());
            map.put("作品中文描述", studioWork.getWorkDesC());
            map.put("作品客户", studioWork.getWorkClient());
            list.add(map);
        }
        FileUtil.downloadExcel(list, response);
    }
}
