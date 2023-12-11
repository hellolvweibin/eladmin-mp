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
import java.sql.Timestamp;
import java.io.Serializable;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import javax.validation.constraints.NotBlank;

/**
* @description /
* @author lv
* @date 2023-12-11
**/
@Data
@TableName("studio_base")
public class StudioBase implements Serializable {

    @TableId(value = "studio_id")
    @ApiModelProperty(value = "工作室id")
    private Long studioId;

    @NotBlank
    @ApiModelProperty(value = "工作室英文名")
    private String studioName;

    @NotBlank
    @ApiModelProperty(value = "工作室中文名")
    private String studioNamec;

    @ApiModelProperty(value = "工作室英文描述")
    private String studioDes;

    @ApiModelProperty(value = "工作室中文描述")
    private String studioDesc;

    @ApiModelProperty(value = "工作室英文地址")
    private String studioLoc;

    @ApiModelProperty(value = "工作室中文地址")
    private String studioLocc;

    @ApiModelProperty(value = "工作室经度")
    private Double studioLoclon;

    @ApiModelProperty(value = "工作室纬度")
    private Double studioLoclat;

    @ApiModelProperty(value = "工作室邮箱")
    private String studioMail;

    @ApiModelProperty(value = "工作室ig")
    private String studioIg;

    @ApiModelProperty(value = "工作室小红书")
    private String studioRed;

    @ApiModelProperty(value = "创建日期")
    private Timestamp createTime;

    @ApiModelProperty(value = "更新时间")
    private Timestamp updateTime;

    public void copy(StudioBase source){
        BeanUtil.copyProperties(source,this, CopyOptions.create().setIgnoreNullValue(true));
    }
}
