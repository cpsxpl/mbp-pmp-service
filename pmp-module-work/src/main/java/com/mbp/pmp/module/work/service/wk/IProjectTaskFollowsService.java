package com.mbp.pmp.module.work.service.wk;

import com.kakarote.common.result.BasePage;
import com.kakarote.common.servlet.BaseService;
import com.mbp.pmp.module.work.domain.entity.bo.ProjectTaskFollowsQueryBO;
import com.mbp.pmp.module.work.domain.entity.vo.ProjectTaskFollowsVO;
import com.mbp.pmp.module.work.domain.model.wk.ProjectTaskFollows;

/**
 * <p>
 * 项目事项跟进记录/客户动态表 服务类
 * </p>
 *
 * @author cpsxpl
 * @since 2022-09-21
 */
public interface IProjectTaskFollowsService extends BaseService<ProjectTaskFollows> {
    BasePage<ProjectTaskFollowsVO> getProjectFollowsPageList(ProjectTaskFollowsQueryBO projectTaskFollowsQueryBO);
}