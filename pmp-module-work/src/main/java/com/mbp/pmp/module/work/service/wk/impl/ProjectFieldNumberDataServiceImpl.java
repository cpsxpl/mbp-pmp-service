package com.mbp.pmp.module.work.service.wk.impl;

import com.kakarote.common.servlet.BaseServiceImpl;
import com.mbp.pmp.module.work.dao.mapper.baseMapper.wk.ProjectFieldNumberDataMapper;
import com.mbp.pmp.module.work.domain.model.wk.ProjectFieldNumberData;
import com.mbp.pmp.module.work.service.wk.IProjectFieldNumberDataService;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 自定义编号字段存值表 服务实现类
 * </p>
 *
 * @author cpsxpl
 * @since 2022-09-16
 */
@Service
public class ProjectFieldNumberDataServiceImpl extends BaseServiceImpl<ProjectFieldNumberDataMapper, ProjectFieldNumberData> implements IProjectFieldNumberDataService {
}