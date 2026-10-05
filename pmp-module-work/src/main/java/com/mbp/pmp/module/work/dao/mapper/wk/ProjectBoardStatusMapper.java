package com.mbp.pmp.module.work.dao.mapper.wk;

import com.kakarote.common.servlet.BaseMapper;
import com.mbp.pmp.module.work.domain.entity.vo.ProjectBoardStatusVO;
import com.mbp.pmp.module.work.domain.model.wk.ProjectBoardStatus;
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
