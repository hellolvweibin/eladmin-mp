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

import me.zhengjie.modules.studio.domain.StudioImage;
import me.zhengjie.utils.FileUtil;
import lombok.RequiredArgsConstructor;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import me.zhengjie.modules.studio.service.StudioImageService;
import me.zhengjie.modules.studio.domain.vo.StudioImageQueryCriteria;
import me.zhengjie.modules.studio.mapper.StudioImageMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import me.zhengjie.utils.PageUtil;

import java.io.File;
import java.util.List;
import java.util.Map;
import java.io.IOException;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import me.zhengjie.utils.PageResult;
import org.springframework.web.multipart.MultipartFile;

/**
* @description 服务实现
* @author lv
* @date 2024-01-28
**/
@Service
@RequiredArgsConstructor
public class StudioImageServiceImpl extends ServiceImpl<StudioImageMapper, StudioImage> implements StudioImageService {

    private final StudioImageMapper studioImageMapper;


    @Override
    public PageResult<StudioImage> queryAll(StudioImageQueryCriteria criteria, Page<Object> page){
        return PageUtil.toPage(studioImageMapper.findAll(criteria, page));
    }

    @Override
    public List<StudioImage> queryAll(StudioImageQueryCriteria criteria){
        return studioImageMapper.findAll(criteria);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void create(StudioImage resources) {
        save(resources);
    }

    @Override
    public StudioImage upload(MultipartFile file, String path) {
        return null;
    }


    @Override
    @Transactional(rollbackFor = Exception.class)
    public void update(StudioImage resources) {
        StudioImage studioImage = getById(resources.getId());
        studioImage.copy(resources);
        saveOrUpdate(studioImage);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteAll(List<Long> ids) {
        removeBatchByIds(ids);
    }

    @Override
    public void download(List<StudioImage> all, HttpServletResponse response) throws IOException {
        List<Map<String, Object>> list = new ArrayList<>();
        for (StudioImage studioImage : all) {
            Map<String,Object> map = new LinkedHashMap<>();
            map.put("图片名称", studioImage.getImageName());
            map.put("图片路径", studioImage.getImagePath());
            list.add(map);
        }
        FileUtil.downloadExcel(list, response);
    }
}
