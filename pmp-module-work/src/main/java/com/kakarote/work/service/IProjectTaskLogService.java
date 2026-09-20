package com.kakarote.work.service;

import com.kakarote.common.servlet.BaseService;
import com.kakarote.work.entity.PO.ProjectTaskLog;
import com.kakarote.work.entity.VO.ProjectTaskLogVO;

import java.util.List;

/**
 * <p>
 * 任务日志表 服务类
 * </p>
 *
 * @author cpsxpl
 * @since 2022-09-14
 */
public interface IProjectTaskLogService extends BaseService<ProjectTaskLog> {
    List<ProjectTaskLogVO> queryTaskLog(Long taskId, Long type);

    void saveTaskLog(ProjectTaskLog projectTaskLog);

    void saveTaskLog(Long taskId, String content);
}