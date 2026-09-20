package com.mbp.eng.module.work.service;

import com.kakarote.common.servlet.BaseService;
import com.mbp.eng.module.work.entity.PO.ProjectEvent;

import java.util.List;

/**
 * <p>
 * 事件表 服务类
 * </p>
 *
 * @author cpsxpl
 * @since 2022-09-19
 */
public interface IProjectEventService extends BaseService<ProjectEvent> {
    void add(ProjectEvent projectEvent);

    void update(ProjectEvent projectEvent);

    void delete(Long id);

    List<ProjectEvent> queryEventList(Long schemeId);
}