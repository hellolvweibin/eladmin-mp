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

import cn.hutool.core.date.DateTime;
import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
import cn.hutool.core.bean.BeanUtil;
import io.swagger.annotations.ApiModelProperty;
import cn.hutool.core.bean.copier.CopyOptions;
import lombok.EqualsAndHashCode;

import java.sql.Timestamp;
import java.io.Serializable;

/**
* @description /
* @author lv
* @date 2023-12-11
**/
@Data
@TableName("studio_award")
public class StudioAward extends Base implements Serializable {

    @TableId(value = "award_id",type = IdType.AUTO)
    @ApiModelProperty(value = "奖项id")
    private Integer awardId;

    @ApiModelProperty(value = "奖项名称")
    private String awardName;

    @ApiModelProperty(value = "奖项分类")
    private String awardCategory;



    public void copy(StudioAward source){
        BeanUtil.copyProperties(source,this, CopyOptions.create().setIgnoreNullValue(true));
    }
}
