package me.zhengjie.modules.studio.domain;

import com.baomidou.mybatisplus.annotation.*;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;


/**
 * @author levi_bee
 */
@Data
@TableName("studio_page_visit")
public class StudioPageVisit {
    @TableId(value = "id", type = IdType.AUTO)
    @ApiModelProperty(value = "id")
    private Long id;

    @ApiModelProperty(value = "访问时间")
    private LocalDateTime visitTime;

    @ApiModelProperty(value = "访问日期")
    private LocalDate visitDate;

}
