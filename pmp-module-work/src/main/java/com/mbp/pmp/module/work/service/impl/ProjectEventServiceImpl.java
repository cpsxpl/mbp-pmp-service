package com.mbp.pmp.module.work.service.impl;

import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.StrUtil;
import com.kakarote.common.servlet.BaseServiceImpl;
import com.kakarote.common.utils.UserUtil;
import com.mbp.pmp.module.work.domain.ProjectEvent;
import com.mbp.pmp.module.work.domain.ProjectSchemeRelation;
import com.mbp.pmp.module.work.dao.mapper.ProjectEventMapper;
import com.mbp.pmp.module.work.service.IProjectEventService;
import com.mbp.pmp.module.work.service.IProjectSchemeRelationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 事件表 服务实现类
 * </p>
 *
 * @author cpsxpl
 * @since 2022-09-19
 */
@Service
public class ProjectEventServiceImpl extends BaseServiceImpl<ProjectEventMapper, ProjectEvent> implements IProjectEventService {
    @Autowired
    private IProjectSchemeRelationService schemeRelationService;

    @Override
    public void add(ProjectEvent projectEvent) {
        if (StrUtil.isNotEmpty(projectEvent.getBatchId())) {
            projectEvent.setBatchId(IdUtil.simpleUUID());
        }
        projectEvent.setCreateUserId(UserUtil.getUserId());
        save(projectEvent);
    }

    @Override
    public void update(ProjectEvent projectEvent) {
        projectEvent.setUpdateUserId(UserUtil.getUserId());
        //  update(projectEvent);
    }

    @Override
    public void delete(Long schemeId) {
        removeById(schemeId);
    }

    @Override
    public List<ProjectEvent> queryEventList(Long schemeId) {
        List<Long> eventIds = new ArrayList<>();
        schemeRelationService.lambdaQuery().eq(ProjectSchemeRelation::getSchemeId, schemeId).select(ProjectSchemeRelation::getEventId).list().forEach(
                sr -> {
                    eventIds.add(sr.getEventId());
                }
        );
        List<ProjectEvent> events = lambdaQuery().in(ProjectEvent::getId, eventIds).list();
        return events;
    }
}