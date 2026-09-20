package com.mbp.pmp.module.work.dao.mapper;

import com.baomidou.mybatisplus.annotation.InterceptorIgnore;
import com.kakarote.common.servlet.BaseMapper;
import com.mbp.pmp.module.work.common.project.ProjectCountBO;
import com.mbp.pmp.module.work.entity.BO.ProjectOwnerRoleSimpleBO;
import com.mbp.pmp.module.work.domain.Project;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 项目表 Mapper 接口
 * </p>
 *
 * @author cpsxpl
 * @since 2022-09-08
 */
public interface ProjectMapper extends BaseMapper<Project> {
    String queryRoleName(@Param("roleId") Long roleId);

    List<ProjectOwnerRoleSimpleBO> queryOwnerRoleList(@Param("projectId") Long projectId);

    /**
     * 功能描述: <br>
     * 〈根据项目ids查询项目统计项〉
     *
     * @param projectIds
     * @return java.util.List<com.kakarote.work.entity.BO.ProjectCountBO>
     * @author cpsxpl
     */
    List<ProjectCountBO> getProjectCount(@Param("projectIds") List<Long> projectIds, @Param("userId") Long userId);

    @InterceptorIgnore(tenantLine = "1")
    List<Project> allProject();
}