package com.kakarote.work.service;

import com.kakarote.common.result.BasePage;
import com.kakarote.common.servlet.BaseService;
import com.kakarote.work.common.project.ProjectOwnerRoleBO;
import com.kakarote.work.entity.BO.ProjectQueryBO;
import com.kakarote.work.entity.BO.ProjectVo;
import com.kakarote.work.entity.PO.Project;
import org.springframework.web.bind.annotation.PathVariable;

import javax.validation.constraints.NotNull;

import java.util.List;

/**
 * <p>
 * 项目表 服务类
 * </p>
 *
 * @author cpsxpl
 * @since 2022-09-08
 */
public interface IProjectService extends BaseService<Project> {
    Project addProject(Project project);

    List<ProjectOwnerRoleBO> queryOwnerRoleList(Long projectId);

    BasePage<Project> iParticipateProjectList(ProjectQueryBO projectQueryBO);

    BasePage<Project> iManagementProjectList(ProjectQueryBO projectQueryBO);

    BasePage<Project> allProjectList(ProjectQueryBO projectQueryBO);

    Project updateProject(ProjectVo projectVo);

    ProjectOwnerRoleBO queryOpenAuthEdit(@PathVariable @NotNull Long projectId);

    Project getProjectById(Long projectId, Long taskId);

    void deleteProject(Long projectId);

    BasePage<Project> queryProjectList(ProjectQueryBO projectQueryBO);

    void archiveProject(Long projectId, Integer setType);

    BasePage<Project> archiveProjectList(ProjectQueryBO projectQueryBO);

    BasePage<Project> myProjectList(ProjectQueryBO projectQueryBO);

    void initEventStatus(Long projectId, Long eventId);
}