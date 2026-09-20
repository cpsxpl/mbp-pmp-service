package com.mbp.eng.module.work.service.impl;

import com.kakarote.common.servlet.BaseServiceImpl;
import com.mbp.eng.module.work.entity.PO.AdminMenu;
import com.mbp.eng.module.work.mapper.ProjectMenuMapper;
import com.mbp.eng.module.work.service.IProjectMenuService;
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
