package com.mbp.pmp.module.work.service;

import com.kakarote.common.servlet.BaseService;
import com.mbp.pmp.module.work.entity.PO.ProjectLabel;

import java.util.List;

/**
 * <p>
 * 任务标签表 服务类
 * </p>
 *
 * @author cpsxpl
 * @since 2022-09-20
 */
public interface IProjectLabelService extends BaseService<ProjectLabel> {
    void add(ProjectLabel projectLabel);

    void update(ProjectLabel projectLabel);

    void delete(Long id);

    List<ProjectLabel> queryTaskLabelList(String ids);

    List<ProjectLabel> queryList(String name);
}
