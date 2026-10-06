package com.mbp.pmp.module.work.service.wk.impl;

import com.kakarote.common.servlet.BaseServiceImpl;
import com.mbp.pmp.module.work.dao.mybatis.mapper.baseMapper.wk.ProjectMenuMapper;
import com.mbp.pmp.module.work.domain.model.wk.AdminMenu;
import com.mbp.pmp.module.work.service.wk.IProjectMenuService;
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
