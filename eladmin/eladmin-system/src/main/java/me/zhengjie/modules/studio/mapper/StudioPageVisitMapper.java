package me.zhengjie.modules.studio.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import me.zhengjie.modules.studio.domain.StudioPageVisit;
import me.zhengjie.modules.studio.domain.StudioPartner;
import me.zhengjie.modules.studio.domain.vo.StudioPartnerQueryCriteria;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @Project ：eladmin
 * @Author ：Levi_Bee
 * @Date ：2024/2/6 13:58
 * @description ：
 */
@Mapper
public interface StudioPageVisitMapper extends BaseMapper<StudioPageVisit> {

}
