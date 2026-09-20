package com.mbp.pmp.module.work.service;

import com.kakarote.common.servlet.BaseService;
import com.mbp.pmp.module.work.entity.PO.ProjectSchemeRelation;

/**
 * <p>
 * 项目配置方案和事件关系表 服务类
 * </p>
 *
 * @author cpsxpl
 * @since 2022-09-19
 */
public interface IProjectSchemeRelationService extends BaseService<ProjectSchemeRelation> {
    ProjectSchemeRelation queryEventId(Long SchemeId, Integer TaskType);
}
