package com.mbp.pmp.module.work.service.impl;

import com.kakarote.common.servlet.BaseServiceImpl;
import com.mbp.pmp.module.work.domain.ProjectEventField;
import com.mbp.pmp.module.work.dao.mapper.ProjectEventFieldMapper;
import com.mbp.pmp.module.work.service.IProjectEventFieldService;
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
public class ProjectEventFieldServiceImpl extends BaseServiceImpl<ProjectEventFieldMapper, ProjectEventField> implements IProjectEventFieldService {
}