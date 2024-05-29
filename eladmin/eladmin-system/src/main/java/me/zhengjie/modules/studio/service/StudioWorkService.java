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
package me.zhengjie.modules.studio.service;

import me.zhengjie.modules.studio.domain.StudioBase;
import me.zhengjie.modules.studio.domain.StudioWork;
import me.zhengjie.modules.studio.domain.dto.StudioWorkDTO;
import me.zhengjie.modules.studio.domain.vo.StudioWorkQueryCriteria;
import java.util.Map;
import java.util.List;
import java.io.IOException;
import javax.servlet.http.HttpServletResponse;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import me.zhengjie.modules.system.domain.User;
import me.zhengjie.utils.PageResult;
import org.springframework.transaction.annotation.Transactional;

/**
* @description 服务接口
* @author lv
* @date 2023-12-11
**/
public interface StudioWorkService extends IService<StudioWork> {
    /**
     * 根据id查询作品信息
     * @param id 条件
     * @return StudioWork
     */
    StudioWork findById(Long id);

    /**
    * 查询数据分页
    * @param criteria 条件
    * @param page 分页参数
    * @return PageResult
    */
    PageResult<StudioWork> queryAll(StudioWorkQueryCriteria criteria, Page<Object> page);

    /**
    * 查询所有数据不分页
    * @param criteria 条件参数
    * @return List<StudioWorkDto>
    */
    List<StudioWork> queryAll(StudioWorkQueryCriteria criteria);

    List<StudioWork> findAllByNameC(String workNameC);

    PageResult<StudioWork> findAllByTags(String workTags, String order);
    /**
     * 查询关联数据分页
     * @param criteria 条件
     * @param page 分页参数
     * @return PageResult
     */
    PageResult<StudioWorkDTO> queryAllSet(StudioWorkQueryCriteria criteria, Page<Object> page);


    /**
    * 创建
    * @param resources /
    */
    void create(StudioWork resources);

    /**
    * 编辑
    * @param resources /
    */
    void update(StudioWork resources);

    /**
    * 多选删除
    * @param ids /
    */
    void deleteAll(List<Long> ids);

    /**
    * 导出数据
    * @param all 待导出的数据
    * @param response /
    * @throws IOException /
    */
    void download(List<StudioWork> all, HttpServletResponse response) throws IOException;
}
