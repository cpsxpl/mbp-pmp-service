package com.mbp.pmp.module.work.mapper;

import com.kakarote.common.servlet.BaseMapper;
import com.mbp.pmp.module.work.entity.PO.ProjectBoardStatus;
import com.mbp.pmp.module.work.entity.VO.ProjectBoardStatusVO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 项目管理 项目看板状态 Mapper 接口
 * </p>
 *
 * @author cpsxpl
 * @since 2022-09-22
 */
public interface ProjectBoardStatusMapper extends BaseMapper<ProjectBoardStatus> {
    List<ProjectBoardStatusVO> queryBoardStatusByBoardId(@Param("boardId") Long boardId);

    Long queryBoardStatusIdByBoardId(@Param("boardId") Long boardId);

    ProjectBoardStatusVO queryBoardStatusByStatusId(@Param("projectBoardStatusId") Long projectBoardStatusId);
}
