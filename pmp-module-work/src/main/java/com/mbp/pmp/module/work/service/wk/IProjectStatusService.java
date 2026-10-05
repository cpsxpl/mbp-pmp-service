package com.mbp.pmp.module.work.service.wk;

import com.kakarote.common.result.BasePage;
import com.kakarote.common.servlet.BaseService;
import com.mbp.pmp.module.work.domain.entity.bo.ProjectStatusQueryBO;
import com.mbp.pmp.module.work.domain.model.wk.ProjectStatus;

import java.util.List;

/**
 * <p>
 * 项目管理：状态表 服务类
 * </p>
 *
 * @author cpsxpl
 * @since 2022-09-22
 */
public interface IProjectStatusService extends BaseService<ProjectStatus> {
    void add(ProjectStatus projectStatus);

    void updateSorting(List<Integer> ids);

    BasePage<ProjectStatus> queryProjectStatusList(ProjectStatusQueryBO query);
}