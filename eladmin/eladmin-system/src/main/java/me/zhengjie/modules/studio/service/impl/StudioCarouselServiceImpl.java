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

import me.zhengjie.modules.studio.domain.StudioCarousel;
import me.zhengjie.utils.FileUtil;
import lombok.RequiredArgsConstructor;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import me.zhengjie.modules.studio.service.StudioCarouselService;
import me.zhengjie.modules.studio.domain.vo.StudioCarouselQueryCriteria;
import me.zhengjie.modules.studio.mapper.StudioCarouselMapper;
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
public class StudioCarouselServiceImpl extends ServiceImpl<StudioCarouselMapper, StudioCarousel> implements StudioCarouselService {

    private final StudioCarouselMapper studioCarouselMapper;

    @Override
    public PageResult<StudioCarousel> queryAll(StudioCarouselQueryCriteria criteria, Page<Object> page){
        return PageUtil.toPage(studioCarouselMapper.findAll(criteria, page));
    }

    @Override
    public List<StudioCarousel> queryAll(StudioCarouselQueryCriteria criteria){
        return studioCarouselMapper.findAll(criteria);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void create(StudioCarousel resources) {
        save(resources);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void update(StudioCarousel resources) {
        StudioCarousel studioCarousel = getById(resources.getId());
        studioCarousel.copy(resources);
        saveOrUpdate(studioCarousel);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteAll(List<Integer> ids) {
        removeBatchByIds(ids);
    }

    @Override
    public void download(List<StudioCarousel> all, HttpServletResponse response) throws IOException {
        List<Map<String, Object>> list = new ArrayList<>();
        for (StudioCarousel studioCarousel : all) {
            Map<String,Object> map = new LinkedHashMap<>();
            map.put("轮播图资源路径", studioCarousel.getCarouselImage());
            list.add(map);
        }
        FileUtil.downloadExcel(list, response);
    }
}
