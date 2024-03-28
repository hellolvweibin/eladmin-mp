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

import com.baomidou.mybatisplus.annotation.IdType;
import lombok.Data;
import cn.hutool.core.bean.BeanUtil;
import io.swagger.annotations.ApiModelProperty;
import cn.hutool.core.bean.copier.CopyOptions;
import java.io.Serializable;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
* @description /
* @author lv
* @date 2023-12-11
**/
@Data
@TableName("studio_carousel")
public class StudioCarousel extends Base implements Serializable {

    @TableId(value = "id", type = IdType.AUTO)
    @ApiModelProperty(value = "id")
    private Long id;

    @NotNull
    @ApiModelProperty(value = "作品id")
    private Long workId;

    @NotBlank
    @ApiModelProperty(value = "轮播图地址")
    private String carouselImage;

    @NotBlank
    @ApiModelProperty(value = "轮播图尺寸")
    private String carouselStatus;

    @ApiModelProperty(value = "轮播图x坐标")
    private String carouselOffsetX;

    @ApiModelProperty(value = "轮播图y坐标")
    private String carouselOffsetY;

    @ApiModelProperty(value = "轮播图图片高度")
    private String carouselHeight;

    @ApiModelProperty(value = "轮播图图片宽度")
    private String carouselWidth;

    public void copy(StudioCarousel source){
        BeanUtil.copyProperties(source,this, CopyOptions.create().setIgnoreNullValue(true));
    }
}
