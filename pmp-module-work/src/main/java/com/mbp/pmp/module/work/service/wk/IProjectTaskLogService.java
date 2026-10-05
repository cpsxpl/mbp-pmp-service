package com.mbp.pmp.module.work.service.wk;

import com.kakarote.common.servlet.BaseService;
import com.mbp.pmp.module.work.domain.entity.vo.ProjectTaskLogVO;
import com.mbp.pmp.module.work.domain.model.wk.ProjectTaskLog;

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