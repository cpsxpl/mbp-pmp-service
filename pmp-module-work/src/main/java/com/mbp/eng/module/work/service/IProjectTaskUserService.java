package com.mbp.eng.module.work.service;

import com.kakarote.common.servlet.BaseService;
import com.mbp.eng.module.work.entity.BO.ProjectTaskUserBO;
import com.mbp.eng.module.work.entity.PO.ProjectTaskUser;

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