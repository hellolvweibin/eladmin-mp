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

import me.zhengjie.modules.studio.domain.StudioWorkImage;
import me.zhengjie.utils.FileUtil;
import lombok.RequiredArgsConstructor;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import me.zhengjie.modules.studio.service.StudioWorkImageService;
import me.zhengjie.modules.studio.domain.vo.StudioWorkImageQueryCriteria;
import me.zhengjie.modules.studio.mapper.StudioWorkImageMapper;
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
public class StudioWorkImageServiceImpl extends ServiceImpl<StudioWorkImageMapper, StudioWorkImage> implements StudioWorkImageService {

    private final StudioWorkImageMapper studioWorkImageMapper;

    @Override
    public PageResult<StudioWorkImage> queryAll(StudioWorkImageQueryCriteria criteria, Page<Object> page){
        return PageUtil.toPage(studioWorkImageMapper.findAll(criteria, page));
    }

    @Override
    public List<StudioWorkImage> queryAll(StudioWorkImageQueryCriteria criteria){
        return studioWorkImageMapper.findAll(criteria);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void create(StudioWorkImage resources) {
        save(resources);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void update(StudioWorkImage resources) {
        StudioWorkImage studioWorkImage = getById(resources.getId());
        studioWorkImage.copy(resources);
        saveOrUpdate(studioWorkImage);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteAll(List<Long> ids) {
        removeBatchByIds(ids);
    }

    @Override
    public void download(List<StudioWorkImage> all, HttpServletResponse response) throws IOException {
        List<Map<String, Object>> list = new ArrayList<>();
        for (StudioWorkImage studioWorkImage : all) {
            Map<String,Object> map = new LinkedHashMap<>();
            map.put("作品id", studioWorkImage.getWorkId());
            map.put("图片地址", studioWorkImage.getWorkImage());
            map.put("作品尺寸", studioWorkImage.getWorkStatus());
            map.put("作品x坐标", studioWorkImage.getWorkOffsetX());
            map.put("作品y坐标", studioWorkImage.getWorkOffsetY());
            map.put("作品图片高度", studioWorkImage.getWorkImageHeight());
            map.put("作品图片宽度", studioWorkImage.getWorkImageWidth());
            list.add(map);
        }
        FileUtil.downloadExcel(list, response);
    }
}
