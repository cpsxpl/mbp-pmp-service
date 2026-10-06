package com.mbp.pmp.module.work.service.wk.impl;

import com.kakarote.common.servlet.BaseServiceImpl;
import com.mbp.pmp.module.work.dao.mybatis.mapper.baseMapper.wk.ProjectDataMapper;
import com.mbp.pmp.module.work.domain.model.wk.ProjectData;
import com.mbp.pmp.module.work.service.wk.IProjectDataService;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 项目自定义字段存值表 服务实现类
 * </p>
 *
 * @author cpsxpl
 * @since 2022-09-16
 */
@Service
public class ProjectDataServiceImpl extends BaseServiceImpl<ProjectDataMapper, ProjectData> implements IProjectDataService {
}