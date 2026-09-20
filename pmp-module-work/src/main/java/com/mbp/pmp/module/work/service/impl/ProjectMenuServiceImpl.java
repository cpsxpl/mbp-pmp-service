package com.mbp.pmp.module.work.service.impl;

import com.kakarote.common.servlet.BaseServiceImpl;
import com.mbp.pmp.module.work.entity.PO.AdminMenu;
import com.mbp.pmp.module.work.mapper.ProjectMenuMapper;
import com.mbp.pmp.module.work.service.IProjectMenuService;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 事件绑定属性表 服务实现类
 * </p>
 *
 * @author cpsxpl
 * @since 2022-09-20
 */
@Service
public class ProjectMenuServiceImpl extends BaseServiceImpl<ProjectMenuMapper, AdminMenu> implements IProjectMenuService {
}
