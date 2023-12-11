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

import me.zhengjie.modules.studio.domain.StudioClient;
import me.zhengjie.utils.FileUtil;
import lombok.RequiredArgsConstructor;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import me.zhengjie.modules.studio.service.StudioClientService;
import me.zhengjie.modules.studio.domain.vo.StudioClientQueryCriteria;
import me.zhengjie.modules.studio.mapper.StudioClientMapper;
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
public class StudioClientServiceImpl extends ServiceImpl<StudioClientMapper, StudioClient> implements StudioClientService {

    private final StudioClientMapper studioClientMapper;

    @Override
    public PageResult<StudioClient> queryAll(StudioClientQueryCriteria criteria, Page<Object> page){
        return PageUtil.toPage(studioClientMapper.findAll(criteria, page));
    }

    @Override
    public List<StudioClient> queryAll(StudioClientQueryCriteria criteria){
        return studioClientMapper.findAll(criteria);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void create(StudioClient resources) {
        save(resources);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void update(StudioClient resources) {
        StudioClient studioClient = getById(resources.getClientId());
        studioClient.copy(resources);
        saveOrUpdate(studioClient);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteAll(List<Long> ids) {
        removeBatchByIds(ids);
    }

    @Override
    public void download(List<StudioClient> all, HttpServletResponse response) throws IOException {
        List<Map<String, Object>> list = new ArrayList<>();
        for (StudioClient studioClient : all) {
            Map<String,Object> map = new LinkedHashMap<>();
            map.put("客户英文名", studioClient.getClientName());
            map.put("客户中文名", studioClient.getClientNameC());
            map.put("客户标签（用&拼接）", studioClient.getClientTag());
            map.put("客户手机号", studioClient.getClientPhone());
            map.put("客户邮箱", studioClient.getClientMail());
            map.put("创建日期", studioClient.getCreateTime());
            map.put("更新时间", studioClient.getUpdateTime());
            list.add(map);
        }
        FileUtil.downloadExcel(list, response);
    }
}
