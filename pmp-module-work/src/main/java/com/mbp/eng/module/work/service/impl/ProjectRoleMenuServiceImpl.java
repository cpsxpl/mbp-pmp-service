package com.mbp.eng.module.work.service.impl;

import com.kakarote.common.servlet.BaseServiceImpl;
import com.mbp.eng.module.work.common.project.Const;
import com.mbp.eng.module.work.entity.PO.AdminRoleMenu;
import com.mbp.eng.module.work.mapper.ProjectRoleMenuMapper;
import com.mbp.eng.module.work.service.IProjectRoleMenuService;
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