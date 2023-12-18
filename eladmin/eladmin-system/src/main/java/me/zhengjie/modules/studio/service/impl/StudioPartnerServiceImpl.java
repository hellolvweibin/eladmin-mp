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

import me.zhengjie.modules.studio.domain.StudioPartner;
import me.zhengjie.utils.FileUtil;
import lombok.RequiredArgsConstructor;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import me.zhengjie.modules.studio.service.StudioPartnerService;
import me.zhengjie.modules.studio.domain.vo.StudioPartnerQueryCriteria;
import me.zhengjie.modules.studio.mapper.StudioPartnerMapper;
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
public class StudioPartnerServiceImpl extends ServiceImpl<StudioPartnerMapper, StudioPartner> implements StudioPartnerService {

    private final StudioPartnerMapper studioPartnerMapper;

    @Override
    public PageResult<StudioPartner> queryAll(StudioPartnerQueryCriteria criteria, Page<Object> page){
        return PageUtil.toPage(studioPartnerMapper.findAll(criteria, page));
    }

    @Override
    public List<StudioPartner> queryAll(StudioPartnerQueryCriteria criteria){
        return studioPartnerMapper.findAll(criteria);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void create(StudioPartner resources) {
        save(resources);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void update(StudioPartner resources) {
        StudioPartner studioPartner = getById(resources.getPartnerId());
        studioPartner.copy(resources);
        saveOrUpdate(studioPartner);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteAll(List<Long> ids) {
        removeBatchByIds(ids);
    }

    @Override
    public void download(List<StudioPartner> all, HttpServletResponse response) throws IOException {
        List<Map<String, Object>> list = new ArrayList<>();
        for (StudioPartner studioPartner : all) {
            Map<String,Object> map = new LinkedHashMap<>();
            map.put("合作商英文名", studioPartner.getPartnerName());
            map.put("合作商中文名", studioPartner.getPartnerNameC());
            map.put("合作商标签（用&拼接）", studioPartner.getPartnerTag());
            map.put("合作商手机号", studioPartner.getPartnerPhone());
            map.put("合作商邮箱", studioPartner.getPartnerMail());

            list.add(map);
        }
        FileUtil.downloadExcel(list, response);
    }
}
