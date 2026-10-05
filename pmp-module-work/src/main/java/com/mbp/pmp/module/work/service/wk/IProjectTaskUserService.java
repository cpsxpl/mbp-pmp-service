package com.mbp.pmp.module.work.service.wk;

import com.kakarote.common.servlet.BaseService;
import com.mbp.pmp.module.work.domain.entity.bo.ProjectTaskUserBO;
import com.mbp.pmp.module.work.domain.model.wk.ProjectTaskUser;

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