package com.mbp.pmp.module.work.service.wk;

import com.alibaba.fastjson.JSONObject;
import com.kakarote.common.servlet.BaseService;
import com.mbp.pmp.module.work.common.admin.AdminEditProjectRoleBO;
import com.mbp.pmp.module.work.common.admin.AdminProjectRole;
import com.mbp.pmp.module.work.common.admin.AdminProjectRoleBO;
import com.mbp.pmp.module.work.common.project.ProjectOwnerRoleBO;
import com.mbp.pmp.module.work.domain.entity.bo.ProjectRoleQueryBO;
import com.mbp.pmp.module.work.domain.entity.vo.ProjectRolesGroupVO;
import com.mbp.pmp.module.work.domain.model.wk.ProjectUser;

import java.util.List;

/**
 * <p>
 * 项目成员表 服务类
 * </p>
 *
 * @author cpsxpl
 * @since 2022-10-27
 */
public interface IProjectUserService extends BaseService<ProjectUser> {
    void relatedProjectUser(List<AdminProjectRoleBO> adminProjectRoleBOS);

    void editProjectUser(AdminEditProjectRoleBO adminEditProjectRoleBO);

    List<Long> getAllRoleMenu(Long projectId, Long userId);

    List<ProjectRolesGroupVO> getProjectRoles(Long projectId);

    void deleteProjectRoles(AdminProjectRole adminProjectRole);

    List<ProjectOwnerRoleBO> queryProjectUser(ProjectRoleQueryBO projectRoleQueryBO);

    List<Long> queryMyProjectIds();

    List<String> queryProjectAdminUser(Long projectId);

    /**
     * 根据项目id查询项目权限
     *
     * @param projectId
     * @return
     */
    JSONObject getProjectAuth(Long projectId);

    /**
     * 批量查询项目权限
     *
     * @param projectIds
     * @return
     */
    List<JSONObject> projectAuthList(List<Long> projectIds);
}
