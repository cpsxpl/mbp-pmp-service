package com.mbp.pmp.module.work.dao.mapper;

import com.kakarote.common.result.BasePage;
import com.kakarote.common.servlet.BaseMapper;
import com.mbp.pmp.module.work.entity.BO.ProjectTaskFollowsQueryBO;
import com.mbp.pmp.module.work.domain.ProjectTaskFollows;
import com.mbp.pmp.module.work.entity.VO.ProjectTaskFollowsVO;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 * 项目事项跟进记录/客户动态表 Mapper 接口
 * </p>
 *
 * @author cpsxpl
 * @since 2022-09-21
 */
public interface ProjectTaskFollowsMapper extends BaseMapper<ProjectTaskFollows> {
    BasePage<ProjectTaskFollowsVO> getProjectFollowsPageList(BasePage<ProjectTaskFollowsQueryBO> page, @Param("projectTaskFollowsQueryBO") ProjectTaskFollowsQueryBO projectTaskFollowsQueryBO);
}
