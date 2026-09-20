package com.kakarote.work.service;

import com.alibaba.fastjson.JSONObject;
import com.kakarote.common.result.BasePage;
import com.kakarote.common.servlet.BaseService;
import com.kakarote.work.common.project.BatchSetTaskBO;
import com.kakarote.work.common.project.ProjectTaskUserSortBO;
import com.kakarote.work.common.project.ProjectUserTaskQueryBO;
import com.kakarote.work.entity.BO.ProjectTaskCountBO;
import com.kakarote.work.entity.BO.ProjectTaskExportBO;
import com.kakarote.work.entity.BO.ProjectTaskNameBO;
import com.kakarote.work.entity.BO.ProjectTaskQueryBO;
import com.kakarote.work.entity.BO.RelevancyBelongIterationBO;
import com.kakarote.work.entity.BO.RelevancyChildTaskBO;
import com.kakarote.work.entity.BO.RelevancyRelatedDemandIdBO;
import com.kakarote.work.entity.PO.ProjectTask;
import com.kakarote.work.entity.VO.ProjectBoardVO;
import com.kakarote.work.entity.VO.ProjectTaskBurnoutVO;
import com.kakarote.work.entity.VO.ProjectTaskCountVO;
import com.kakarote.work.entity.VO.ProjectTaskEventCountVO;
import com.kakarote.work.entity.VO.ProjectUserTaskCountVO;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.List;

/**
 * <p>
 * 任务表 服务类
 * </p>
 *
 * @author cpsxpl
 * @since 2022-09-08
 */
public interface IProjectTaskService extends BaseService<ProjectTask> {
    void saveProjectTask(ProjectTask projectTask);

    ProjectTask queryProjectTaskById(Long projectTaskId);

    Boolean updateProjectTask(ProjectTask projectTask);

    BasePage<ProjectTask> queryProjectTaskList(ProjectTaskQueryBO projectTaskQueryBO);

    ProjectTaskCountVO getProjectByTime(ProjectTaskCountBO projectTaskQueryBO);

    ProjectTaskCountVO getTaskByTime(ProjectTaskCountBO projectTaskQueryBO);

    List<ProjectTaskBurnoutVO> getTaskBurnout(ProjectTaskCountBO projectTaskQueryBO);

    ProjectTaskEventCountVO getProjectTaskEvent(ProjectTaskCountBO projectTaskCountBO);

    BasePage<ProjectTask> getAllMatters(@RequestBody ProjectTaskQueryBO projectTaskQueryBO);

    BasePage<ProjectTask> getAllMattersByTaskId(@RequestBody ProjectTaskQueryBO projectTaskQueryBO);

    /**
     * 功能描述: 待规划列表
     * 〈〉
     *
     * @Param:
     * @Return:
     * @Author: cpsxpl
     * @Date: 2022/9/28 20:33
     */
    BasePage<ProjectTask> queryProjectPlanTaskList(ProjectTaskQueryBO projectTaskQueryBO);

    /**
     * 功能描述: 迭代列表
     * 〈〉
     *
     * @Param:
     * @Return:
     * @Author: cpsxpl
     * @Date: 2022/9/28 20:33
     */
    BasePage<ProjectTask> queryProjectIterationTaskList(ProjectTaskQueryBO projectTaskQueryBO);

    /**
     * 功能描述: 待规划列表
     * 〈〉
     *
     * @Param:
     * @Return:
     * @Author: cpsxpl
     * @Date: 2022/9/28 20:33
     */
    BasePage<ProjectTask> queryProjectTaskChildList(ProjectTaskQueryBO projectTaskQueryBO);

    void relevancyChildTask(RelevancyChildTaskBO relevancyChildTaskBO);

    ProjectTask getProjectTaskDetails(Long taskId);

    void relevancyBelongIteration(RelevancyBelongIterationBO relevancyBelongIterationBO);

    void relevancyRelatedDemand(RelevancyRelatedDemandIdBO relatedDemandIdBO);

    JSONObject excelImport(MultipartFile file, Long projectId, Integer taskType) throws IOException;

    void downloadExcel(HttpServletResponse response, Integer taskType);

    void projectTaskExport(ProjectTaskExportBO taskExportBO, HttpServletResponse response);

    List<JSONObject> projectTaskExportColumn(Integer taskType);

    /**
     * 删除任务
     */
    void deleteTask(Long taskId);

    /**
     * 迭代,需求,任务下的看板任务列表
     */
    List<ProjectBoardVO> queryProjectTaskChildBoardList(ProjectTaskQueryBO projectTaskQueryBO);

    /**
     * 对待办事项进行排序
     */
    void sortBackLog(ProjectTaskUserSortBO pojectTaskUserSortBO);

    /**
     * 功能描述: 查询当前用户的任务列表
     * 〈〉
     *
     * @Param:
     * @Return:
     * @Author: cpsxpl
     * @Date: 2023/2/25 15:53
     */
    BasePage<ProjectTask> queryUserTaskList(ProjectUserTaskQueryBO userTaskQueryBO);

    Boolean setProgress(ProjectTask projectTask);

    Boolean setPriority(ProjectTask projectTask);

    void setProjectTaskMainUser(@RequestBody ProjectTask projectTask);

    void batchSetProjectTask(BatchSetTaskBO batchSetTaskBO);

    void updateProjectTaskTime(ProjectTask projectTask);

    /**
     * 功能描述: <br>
     * 〈查询工作台中各类型数量〉
     *
     * @param userTaskQueryBO
     * @author cpsxpl
     */
    ProjectUserTaskCountVO queryUserTaskCount(ProjectUserTaskQueryBO userTaskQueryBO);

    void projectTaskSetName(ProjectTaskNameBO projectTaskNameBO);
}
