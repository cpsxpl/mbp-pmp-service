package com.mbp.pmp.module.work.service;

import com.kakarote.common.servlet.BaseService;
import com.mbp.pmp.module.work.domain.ProjectTaskUser;
import com.mbp.pmp.module.work.entity.BO.ProjectTaskUserBO;

/**
 * <p>
 * 项目成员表 服务类
 * </p>
 *
 * @author cpsxpl
 * @since 2022-10-27
 */
public interface IProjectTaskUserService extends BaseService<ProjectTaskUser> {
    void relatedProjectUser(ProjectTaskUserBO projectTaskUserBO);
}