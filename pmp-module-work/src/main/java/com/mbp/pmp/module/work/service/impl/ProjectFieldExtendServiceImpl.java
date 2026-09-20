package com.mbp.pmp.module.work.service.impl;

import com.kakarote.common.servlet.BaseServiceImpl;
import com.mbp.pmp.module.work.domain.ProjectFieldExtend;
import com.mbp.pmp.module.work.dao.mapper.ProjectFieldExtendMapper;
import com.mbp.pmp.module.work.service.IProjectFieldExtendService;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 自定义字段扩展数据表 服务实现类
 * </p>
 *
 * @author cpsxpl
 * @since 2022-09-16
 */
@Service
public class ProjectFieldExtendServiceImpl extends BaseServiceImpl<ProjectFieldExtendMapper, ProjectFieldExtend> implements IProjectFieldExtendService {
}
