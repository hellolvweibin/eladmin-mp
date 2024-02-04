package me.zhengjie.modules.studio.domain;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.bean.copier.CopyOptions;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @Project ：eladmin
 * @Author ：Levi_Bee
 * @Date ：2024/1/31 14:33
 * @description ：
 */
@Data
@TableName("studio_image")
public class StudioImage implements Serializable {
    @TableId(value = "id",type = IdType.AUTO)
    @ApiModelProperty(value = "记录id")
    private Long id;

    @ApiModelProperty(value = "图片名称")
    private String imageName;
    @ApiModelProperty(value = "图片路径")
    private String imagePath;

    public void copy(StudioImage source){
        BeanUtil.copyProperties(source,this, CopyOptions.create().setIgnoreNullValue(true));
    }
}
