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
@TableName("studio_partner")
public class StudioPartner implements Serializable {

    @TableId(value = "partner_id")
    @ApiModelProperty(value = "合作商id")
    private Long partnerId;

    @NotBlank
    @ApiModelProperty(value = "合作商英文名")
    private String partnerName;

    @ApiModelProperty(value = "合作商中文名")
    private String partnerNamec;

    @ApiModelProperty(value = "合作商标签（用&拼接）")
    private String partnerTag;

    @ApiModelProperty(value = "合作商手机号")
    private String partnerPhone;

    @ApiModelProperty(value = "合作商邮箱")
    private String partnerMail;

    @ApiModelProperty(value = "创建日期")
    private Timestamp createTime;

    @ApiModelProperty(value = "更新时间")
    private Timestamp updateTime;

    public void copy(StudioPartner source){
        BeanUtil.copyProperties(source,this, CopyOptions.create().setIgnoreNullValue(true));
    }
}
