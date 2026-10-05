package com.mbp.pmp.module.work.service.wk;

import com.kakarote.common.servlet.BaseService;
import com.mbp.pmp.module.work.domain.model.wk.ProjectGroup;

import java.util.List;

/**
 * <p>
 * 项目分组表 服务类
 * </p>
 *
 * @author cpsxpl
 * @since 2022-09-09
 */
public interface IProjectGroupService extends BaseService<ProjectGroup> {
    void addGroup(ProjectGroup projectGroup);

    void updateGroup(ProjectGroup projectGroup);

    void updateGroupBatch(List<ProjectGroup> projectGroups);

    List<ProjectGroup> searchGroupList();

    void removeGroupById(Long groupId);
}