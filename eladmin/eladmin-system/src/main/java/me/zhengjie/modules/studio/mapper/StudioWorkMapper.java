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
package me.zhengjie.modules.studio.mapper;

import me.zhengjie.modules.studio.domain.StudioWork;
import me.zhengjie.modules.studio.domain.vo.StudioWorkQueryCriteria;
import java.util.List;

import me.zhengjie.modules.system.domain.Job;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Mapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Select;

/**
* @author lv
* @date 2023-12-11
**/
@Mapper
public interface StudioWorkMapper extends BaseMapper<StudioWork> {
    @Select("select work_id from studio_work where name = #{name}")
    StudioWork findByName(@Param("name") String name);

    IPage<StudioWork> findAll(@Param("criteria") StudioWorkQueryCriteria criteria, Page<Object> page);

    List<StudioWork> findAll(@Param("criteria") StudioWorkQueryCriteria criteria);
}
