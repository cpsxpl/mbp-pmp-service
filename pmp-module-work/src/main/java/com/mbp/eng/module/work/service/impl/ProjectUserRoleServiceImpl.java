package com.mbp.eng.module.work.service.impl;

import cn.hutool.core.convert.Convert;
import com.kakarote.common.servlet.BaseServiceImpl;
import com.mbp.eng.module.work.common.project.Const;
import com.mbp.eng.module.work.entity.PO.AdminRole;
import com.mbp.eng.module.work.entity.PO.AdminUserRole;
import com.mbp.eng.module.work.mapper.ProjectUserRoleMapper;
import com.mbp.eng.module.work.service.IProjectRoleService;
import com.mbp.eng.module.work.service.IProjectUserRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * <p>
 * 用户角色对应关系表 服务实现类
 * </p>
 *
 * @author cpsxpl
 * @since 2020-04-27
 */
@Service
public class ProjectUserRoleServiceImpl extends BaseServiceImpl<ProjectUserRoleMapper, AdminUserRole> implements IProjectUserRoleService {
    @Autowired
    private IProjectRoleService adminRoleService;

    /**
     * 通过userID删除该用户的所有
     *
     * @param userId   用户ID
     * @param isRemove 是否删除原有角色
     * @param roleIds  角色列表
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void saveByUserId(Long userId, boolean isRemove, List<String> roleIds) {
        //当前能设置的角色除了授权能看到的角色，还包含之前用户的历史角色,防止角色被意外删除
        List<Long> longs = lambdaQuery().select(AdminUserRole::getRoleId).eq(AdminUserRole::getUserId, userId).list().stream().map(AdminUserRole::getRoleId).collect(Collectors.toList());
        if (isRemove) {
            lambdaUpdate().eq(AdminUserRole::getUserId, userId).remove();
        }
        List<AdminUserRole> adminUserRoleList = new ArrayList<>();
        for (String roleId : roleIds) {
            Long id = Convert.toLong(roleId);
            adminUserRoleList.add(new AdminUserRole().setUserId(userId).setRoleId(id));
        }
        saveBatch(adminUserRoleList, Const.BATCH_SAVE_SIZE);
    }

    @Override
    public void saveByUserId(Long userId) {
        List<AdminRole> roles = adminRoleService.lambdaQuery().eq(AdminRole::getRoleName, "默认角色")
                .eq(AdminRole::getStatus, 1).list();
        if (roles.size() == 0) {
            AdminRole adminRole = adminRoleService.queryDefaultRole();
            roles.add(adminRole);
        }
        AdminUserRole adminUserRole = new AdminUserRole().setUserId(userId).setRoleId(roles.get(0).getRoleId());
        save(adminUserRole);
    }
}
