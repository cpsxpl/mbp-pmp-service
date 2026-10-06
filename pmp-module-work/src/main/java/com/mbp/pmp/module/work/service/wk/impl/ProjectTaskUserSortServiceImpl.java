package com.mbp.pmp.module.work.service.wk.impl;

import com.kakarote.common.servlet.BaseServiceImpl;
import com.mbp.pmp.module.work.dao.mybatis.mapper.baseMapper.wk.ProjectTaskUserSortMapper;
import com.mbp.pmp.module.work.domain.model.wk.ProjectTaskUserSort;
import com.mbp.pmp.module.work.service.wk.IProjectTaskUserSortService;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 项目成员表 服务实现类
 * </p>
 *
 * @author cpsxpl
 * @since 2022-10-27
 */
@Service
public class ProjectTaskUserSortServiceImpl extends BaseServiceImpl<ProjectTaskUserSortMapper, ProjectTaskUserSort> implements IProjectTaskUserSortService {
}
