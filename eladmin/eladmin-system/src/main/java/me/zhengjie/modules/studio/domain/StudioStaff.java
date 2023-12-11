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
@TableName("studio_staff")
public class StudioStaff implements Serializable {

    @TableId(value = "staff_id")
    @ApiModelProperty(value = "员工id")
    private Long staffId;

    @ApiModelProperty(value = "员工英文名")
    private String staffName;

    @NotBlank
    @ApiModelProperty(value = "员工中文名")
    private String staffNameC;

    @ApiModelProperty(value = "员工性别（0、未知，1、男，2、女）")
    private Integer staffSex;

    @ApiModelProperty(value = "员工工作室id")
    private Long staffStudioId;

    @ApiModelProperty(value = "创建日期")
    private Timestamp createTime;

    @ApiModelProperty(value = "更新时间")
    private Timestamp updateTime;

    public void copy(StudioStaff source){
        BeanUtil.copyProperties(source,this, CopyOptions.create().setIgnoreNullValue(true));
    }
}
