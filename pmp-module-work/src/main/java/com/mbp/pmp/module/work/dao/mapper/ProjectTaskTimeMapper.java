package com.mbp.pmp.module.work.dao.mapper;

import com.kakarote.common.servlet.BaseMapper;
import com.mbp.pmp.module.work.domain.ProjectTaskTime;
import com.mbp.pmp.module.work.entity.BO.ProjectGanttQueryBO;
import com.mbp.pmp.module.work.entity.VO.ProjectTaskTimeListVO;
import com.mbp.pmp.module.work.entity.VO.ProjectTaskTimeMaxAndMinVO;
import com.mbp.pmp.module.work.entity.VO.ProjectTaskTimeVO;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 项目事项工时表 Mapper 接口
 * </p>
 *
 * @author cpsxpl
 * @since 2022-09-21
 */
public interface ProjectTaskTimeMapper extends BaseMapper<ProjectTaskTime> {
    /**
     * 获取项目事项工时列表
     *
     * @param projectGanttQueryBO
     * @return
     */
    List<ProjectTaskTimeListVO> queryProjectTaskTimeList(@Param("projectGanttQueryBO") ProjectGanttQueryBO projectGanttQueryBO);

    /**
     * 获取工时
     *
     * @param taskId
     * @param projectGanttQueryBO
     * @return
     */
    List<Map<String, Object>> queryProjectTaskTime(@Param("taskId") Long taskId, @Param("projectGanttQueryBO") ProjectGanttQueryBO projectGanttQueryBO);

    /**
     * 根据项目事项ID获取工时
     *
     * @param taskId
     * @return
     */
    ProjectTaskTimeVO queryProjectTaskTimeByTaskId(@Param("taskId") Long taskId);

    ProjectTaskTimeMaxAndMinVO queryProjectTaskTimeMaxAndMin(@Param("projectId") Long projectId);
}
