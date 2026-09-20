package com.mbp.pmp.module.work.service.impl;

import com.kakarote.common.servlet.BaseServiceImpl;
import com.mbp.pmp.module.work.entity.PO.ProjectData;
import com.mbp.pmp.module.work.mapper.ProjectDataMapper;
import com.mbp.pmp.module.work.service.IProjectDataService;
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