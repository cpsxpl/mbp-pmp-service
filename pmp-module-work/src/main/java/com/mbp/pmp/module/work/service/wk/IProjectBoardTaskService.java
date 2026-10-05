package com.mbp.pmp.module.work.service.wk;

import com.mbp.pmp.module.work.domain.entity.bo.ProjectBoardTaskBO;
import com.mbp.pmp.module.work.domain.entity.vo.ProjectBoardVO;

import java.util.List;

/**
 * <p>
 * 项目管理 看板信息 服务类
 * </p>
 *
 * @author cpsxpl
 * @since 2022-09-22
 */
public interface IProjectBoardTaskService {
    List<ProjectBoardVO> queryBoardTaskList(ProjectBoardTaskBO boardTaskBO);

    void setTaskStatus(Long taskId, Long statusId);
}