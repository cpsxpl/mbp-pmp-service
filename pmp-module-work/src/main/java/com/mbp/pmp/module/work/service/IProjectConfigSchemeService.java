package com.mbp.pmp.module.work.service;

import com.kakarote.common.servlet.BaseService;
import com.mbp.pmp.module.work.domain.ProjectConfigScheme;
import com.mbp.pmp.module.work.domain.ProjectEvent;

import java.util.List;

/**
 * <p>
 * 项目配置方案表 服务类
 * </p>
 *
 * @author cpsxpl
 * @since 2022-09-19
 */
public interface IProjectConfigSchemeService extends BaseService<ProjectConfigScheme> {
    void add(ProjectConfigScheme projectConfigScheme);

    void update(ProjectConfigScheme projectConfigScheme);

    void delete(Long schemeId);

    List<ProjectEvent> queryBySchemeId(Long schemeId);
}
