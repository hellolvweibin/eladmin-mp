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

import me.zhengjie.modules.studio.domain.StudioAward;
import me.zhengjie.utils.FileUtil;
import lombok.RequiredArgsConstructor;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import me.zhengjie.modules.studio.service.StudioAwardService;
import me.zhengjie.modules.studio.domain.vo.StudioAwardQueryCriteria;
import me.zhengjie.modules.studio.mapper.StudioAwardMapper;
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
public class StudioAwardServiceImpl extends ServiceImpl<StudioAwardMapper, StudioAward> implements StudioAwardService {

    private final StudioAwardMapper studioAwardMapper;

    @Override
    public PageResult<StudioAward> queryAll(StudioAwardQueryCriteria criteria, Page<Object> page){
        return PageUtil.toPage(studioAwardMapper.findAll(criteria, page));
    }

    @Override
    public List<StudioAward> queryAll(StudioAwardQueryCriteria criteria){
        return studioAwardMapper.findAll(criteria);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void create(StudioAward resources) {
        save(resources);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void update(StudioAward resources) {
        StudioAward studioAward = getById(resources.getAwardId());
        studioAward.copy(resources);
        saveOrUpdate(studioAward);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteAll(List<Integer> ids) {
        removeBatchByIds(ids);
    }

    @Override
    public void download(List<StudioAward> all, HttpServletResponse response) throws IOException {
        List<Map<String, Object>> list = new ArrayList<>();
        for (StudioAward studioAward : all) {
            Map<String,Object> map = new LinkedHashMap<>();
            map.put("奖项名称", studioAward.getAwardName());
            map.put("奖项分类", studioAward.getAwardCategory());
            map.put("创建日期", studioAward.getCreateTime());
            map.put("更新时间", studioAward.getUpdateTime());
            list.add(map);
        }
        FileUtil.downloadExcel(list, response);
    }
}