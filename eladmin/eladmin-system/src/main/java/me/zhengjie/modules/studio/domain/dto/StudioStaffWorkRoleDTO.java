package me.zhengjie.modules.studio.domain.dto;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import me.zhengjie.modules.studio.domain.StudioStaff;

/**
 * @Author Yansufeng
 * @Date 2024/5/30 00:53
 */
@Data
public class StudioStaffWorkRoleDTO extends StudioStaff {

    private Long id;

    private String staffTag;
}
