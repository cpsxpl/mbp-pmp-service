package com.mbp.pmp.module.work.service.wk.impl;

import com.kakarote.common.servlet.BaseServiceImpl;
import com.mbp.pmp.module.work.common.project.Const;
import com.mbp.pmp.module.work.dao.mapper.baseMapper.wk.ProjectRoleMenuMapper;
import com.mbp.pmp.module.work.domain.model.wk.AdminRoleMenu;
import com.mbp.pmp.module.work.service.wk.IProjectRoleMenuService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 角色菜单对应关系表 服务实现类
 * </p>
 *
 * @author cpsxpl
 * @since 2020-04-27
 */
@Service
public class ProjectRoleMenuServiceImpl extends BaseServiceImpl<ProjectRoleMenuMapper, AdminRoleMenu> implements IProjectRoleMenuService {
    @Override
    public void saveRoleMenu(Long roleId, List<Long> menuIdList) {
        List<AdminRoleMenu> adminRoleMenuList = new ArrayList<>();
        menuIdList.forEach(menuId -> {
            AdminRoleMenu adminRoleMenu = new AdminRoleMenu();
            adminRoleMenu.setMenuId(menuId);
            adminRoleMenu.setRoleId(roleId);
            adminRoleMenuList.add(adminRoleMenu);
        });
        saveBatch(adminRoleMenuList, Const.BATCH_SAVE_SIZE);
    }
}