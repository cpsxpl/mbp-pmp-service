package com.mbp.pmp.module.work.service.wk;

import com.kakarote.common.servlet.BaseService;
import com.mbp.pmp.module.work.domain.model.wk.Project;
import com.mbp.pmp.module.work.domain.model.wk.ProjectGroupManagement;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * <p>
 * 项目分组管理表 服务类
 * </p>
 *
 * @author cpsxpl
 * @since 2022-09-17
 */
public interface IProjectGroupManagementService extends BaseService<ProjectGroupManagement> {
    void moveToGroup(ProjectGroupManagement projectGroupManagement);

    void removeToGroup(Long groupId, Long projectId);

    List<Project> searchProjectGroupList(@RequestParam Long groupId);
}
