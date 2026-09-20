package com.mbp.pmp.module.work.service;

import com.kakarote.common.result.BasePage;
import com.kakarote.common.servlet.BaseService;
import com.mbp.pmp.module.work.entity.BO.ProjectTaskFollowsQueryBO;
import com.mbp.pmp.module.work.entity.PO.ProjectTaskFollows;
import com.mbp.pmp.module.work.entity.VO.ProjectTaskFollowsVO;

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