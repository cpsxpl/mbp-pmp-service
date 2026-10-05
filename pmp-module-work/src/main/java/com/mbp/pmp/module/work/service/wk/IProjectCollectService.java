package com.mbp.pmp.module.work.service.wk;

import com.kakarote.common.result.BasePage;
import com.kakarote.common.servlet.BaseService;
import com.mbp.pmp.module.work.domain.entity.bo.ProjectQueryBO;
import com.mbp.pmp.module.work.domain.model.wk.Project;
import com.mbp.pmp.module.work.domain.model.wk.ProjectCollect;

import java.util.List;

/**
 * <p>
 * 项目收藏表 服务类
 * </p>
 *
 * @author cpsxpl
 * @since 2022-09-08
 */
public interface IProjectCollectService extends BaseService<ProjectCollect> {
    void collect(Long projectId);

    List<ProjectCollect> queryCollectByProjectId(Long projectId);

    BasePage<Project> myCollectByProjectList(ProjectQueryBO projectQueryBO);
}