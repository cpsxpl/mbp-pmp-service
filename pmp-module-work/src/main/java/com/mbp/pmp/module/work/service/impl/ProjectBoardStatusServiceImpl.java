package com.mbp.pmp.module.work.service.impl;

import com.kakarote.common.servlet.BaseServiceImpl;
import com.mbp.pmp.module.work.entity.PO.ProjectBoardStatus;
import com.mbp.pmp.module.work.entity.VO.ProjectBoardStatusVO;
import com.mbp.pmp.module.work.mapper.ProjectBoardStatusMapper;
import com.mbp.pmp.module.work.service.IProjectBoardStatusService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 项目管理 项目看板状态 服务实现类
 *
 * @author cpsxpl
 * @since 2022-09-22
 */
@Service
public class ProjectBoardStatusServiceImpl extends BaseServiceImpl<ProjectBoardStatusMapper, ProjectBoardStatus> implements IProjectBoardStatusService {
    public List<ProjectBoardStatusVO> queryBoardStatusByBoardId(Long boardId) {
        return baseMapper.queryBoardStatusByBoardId(boardId);
    }

    @Override
    public Long queryBoardStatusIdByBoardId(Long boardId) {
        return baseMapper.queryBoardStatusIdByBoardId(boardId);
    }

    @Override
    public ProjectBoardStatusVO queryBoardStatusByStatusId(Long projectBoardStatusId) {
        return baseMapper.queryBoardStatusByStatusId(projectBoardStatusId);
    }
}
