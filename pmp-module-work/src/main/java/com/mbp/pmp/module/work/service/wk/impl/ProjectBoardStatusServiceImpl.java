package com.mbp.pmp.module.work.service.wk.impl;

import com.kakarote.common.servlet.BaseServiceImpl;
import com.mbp.pmp.module.work.dao.mapper.baseMapper.wk.ProjectBoardStatusMapper;
import com.mbp.pmp.module.work.domain.entity.vo.ProjectBoardStatusVO;
import com.mbp.pmp.module.work.domain.model.wk.ProjectBoardStatus;
import com.mbp.pmp.module.work.service.wk.IProjectBoardStatusService;
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
