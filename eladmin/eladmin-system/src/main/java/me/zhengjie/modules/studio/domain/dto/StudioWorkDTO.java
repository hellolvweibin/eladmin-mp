package me.zhengjie.modules.studio.domain.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import me.zhengjie.modules.studio.domain.Base;

import java.io.Serializable;

/**
 * @Project ：eladmin
 * @Author ：Levi_Bee
 * @Date ：2023/12/21 14:58
 * @description ：
 */
@Data
public class StudioWorkDTO extends Base implements Serializable {
    @ApiModelProperty(value = "作品id")
    private Long workId;

    @ApiModelProperty(value = "作品英文名")
    private String workName;

    @ApiModelProperty(value = "作品中文名")
    private String workNameC;

    @ApiModelProperty(value = "作品类别")
    private String workType;

    @ApiModelProperty(value = "作品英文描述")
    private String workDes;

    @ApiModelProperty(value = "作品中文描述")
    private String workDesC;

    @ApiModelProperty(value = "作品客户")
    private String workClient;
}
