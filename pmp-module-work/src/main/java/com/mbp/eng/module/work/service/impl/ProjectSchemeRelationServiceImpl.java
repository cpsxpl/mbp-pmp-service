package com.mbp.eng.module.work.service.impl;

import cn.hutool.core.util.ObjectUtil;
import com.kakarote.common.servlet.BaseServiceImpl;
import com.mbp.eng.module.work.entity.PO.ProjectEvent;
import com.mbp.eng.module.work.entity.PO.ProjectSchemeRelation;
import com.mbp.eng.module.work.mapper.ProjectSchemeRelationMapper;
import com.mbp.eng.module.work.service.IProjectEventService;
import com.mbp.eng.module.work.service.IProjectSchemeRelationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 项目配置方案和事件关系表 服务实现类
 * </p>
 *
 * @author cpsxpl
 * @since 2022-09-19
 */
@Service
public class ProjectSchemeRelationServiceImpl extends BaseServiceImpl<ProjectSchemeRelationMapper, ProjectSchemeRelation> implements IProjectSchemeRelationService {
    @Autowired
    private IProjectEventService projectEventService;
    @Override
    public ProjectSchemeRelation queryEventId(Long SchemeId, Integer TaskType) {
        //查询事件ID
        ProjectEvent projectEvent = projectEventService.lambdaQuery().eq(ProjectEvent::getType, TaskType - 1).one();
        if (ObjectUtil.isNotEmpty(projectEvent)) {
            return lambdaQuery().eq(ProjectSchemeRelation::getEventId, projectEvent.getId()).eq(ProjectSchemeRelation::getSchemeId, SchemeId).one();
        }
        return null;
    }
}