package com.mbp.eng.module.work.service;

import com.kakarote.common.servlet.BaseService;
import com.mbp.eng.module.work.entity.PO.ProjectBoardStatus;
import com.mbp.eng.module.work.entity.VO.ProjectBoardStatusVO;

import java.util.List;

/**
 * <p>
 * 项目管理 项目看板状态 服务类
 * </p>
 *
 * @author cpsxpl
 * @since 2022-09-22
 */
public interface IProjectBoardStatusService extends BaseService<ProjectBoardStatus> {
    List<ProjectBoardStatusVO> queryBoardStatusByBoardId(Long boardId);

    Long queryBoardStatusIdByBoardId(Long boardId);

    ProjectBoardStatusVO queryBoardStatusByStatusId(Long projectBoardStatusId);
}
