package com.mbp.pmp.module.work.service.wk.impl;

import com.kakarote.common.servlet.BaseServiceImpl;
import com.mbp.pmp.module.work.dao.mybatis.mapper.baseMapper.wk.ProjectEventFieldMapper;
import com.mbp.pmp.module.work.domain.model.wk.ProjectEventField;
import com.mbp.pmp.module.work.service.wk.IProjectEventFieldService;
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