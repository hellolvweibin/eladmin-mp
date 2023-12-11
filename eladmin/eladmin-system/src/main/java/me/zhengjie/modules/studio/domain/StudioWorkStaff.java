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
package me.zhengjie.modules.studio.domain;

import lombok.Data;
import cn.hutool.core.bean.BeanUtil;
import io.swagger.annotations.ApiModelProperty;
import cn.hutool.core.bean.copier.CopyOptions;
import java.io.Serializable;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import javax.validation.constraints.NotNull;

/**
* @description /
* @author lv
* @date 2023-12-11
**/
@Data
@TableName("studio_work_staff")
public class StudioWorkStaff implements Serializable {

    @TableId(value = "id")
    @ApiModelProperty(value = "记录id")
    private Long id;

    @NotNull
    @ApiModelProperty(value = "作品id")
    private Long workId;

    @NotNull
    @ApiModelProperty(value = "成员id")
    private Long staffId;

    @ApiModelProperty(value = "成员tag")
    private String staffTag;

    @ApiModelProperty(value = "成员英文名")
    private String staffName;

    @ApiModelProperty(value = "成员中文名")
    private String staffNameC;

    public void copy(StudioWorkStaff source){
        BeanUtil.copyProperties(source,this, CopyOptions.create().setIgnoreNullValue(true));
    }
}
