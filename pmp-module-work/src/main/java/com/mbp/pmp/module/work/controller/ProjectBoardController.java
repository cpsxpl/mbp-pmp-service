package com.mbp.pmp.module.work.controller;

import com.kakarote.common.result.Result;
import com.mbp.pmp.module.work.entity.BO.ResetBoardBO;
import com.mbp.pmp.module.work.entity.PO.Project;
import com.mbp.pmp.module.work.entity.PO.ProjectEventStatus;
import com.mbp.pmp.module.work.entity.PO.ProjectSchemeRelation;
import com.mbp.pmp.module.work.entity.VO.ProjectBoardVO;
import com.mbp.pmp.module.work.service.IProjectBoardService;
import com.mbp.pmp.module.work.service.IProjectSchemeRelationBoardService;
import com.mbp.pmp.module.work.service.IProjectSchemeRelationService;
import com.mbp.pmp.module.work.service.IProjectService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.Serializable;
import java.util.List;

/**
 * <p>
 * 项目管理 看板信息
 * </p>
 *
 * @author cpsxpl
 * @since 2022-09-22
 */
@RestController
@RequestMapping("/projectBoard")
@Api(tags = "项目管理：看板信息")
public class ProjectBoardController implements Serializable {
    @Autowired
    IProjectSchemeRelationBoardService srb;
    @Autowired
    IProjectBoardService boardService;
    @Autowired
    IProjectSchemeRelationService projectSchemeRelationService;
    @Autowired
    IProjectService projectService;

    @PostMapping("/queryBoardList")  //燃尽 趋势图 公开
    @ApiOperation("看板设置列表")
    public Result queryBoardList(@RequestParam("projectId") Long projectId, @RequestParam("taskType") Integer taskType) {
        Project project = projectService.getById(projectId);

        ProjectSchemeRelation projectSchemeRelation = projectSchemeRelationService.queryEventId(project.getSchemeId(), taskType);
        List<ProjectBoardVO> projectBoardVOS = srb.queryBoardList(projectSchemeRelation.getId(), projectId);
        return Result.ok(projectBoardVOS);
    }

    @PostMapping("/resetBoard")
    @ApiOperation("重置看板")
    public Result resetBoard(@RequestBody ResetBoardBO resetBoardBO) {
        Project project = projectService.getById(resetBoardBO.getProjectId());
        ProjectSchemeRelation projectSchemeRelation = projectSchemeRelationService.queryEventId(project.getSchemeId(), resetBoardBO.getTaskType());
        resetBoardBO.setSchemeRelationId(projectSchemeRelation.getId());
        srb.resetBoard(resetBoardBO);
        return Result.ok();
    }

    @PostMapping("/initialize")
    @ApiOperation("初始化看板信息")
    public Result initialize() {
        boardService.initialize();
        return Result.ok();
    }

    @PostMapping("/notAddStatus")
    @ApiOperation("未添加的看板状态")
    public Result notAddStatus(@RequestParam("projectId") Long projectId, @RequestParam("taskType") Integer taskType) {
        Project project = projectService.getById(projectId);
        ProjectSchemeRelation projectSchemeRelation = projectSchemeRelationService.queryEventId(project.getSchemeId(), taskType);
        List<ProjectEventStatus> eventStatuses = srb.notAddStatus(projectSchemeRelation.getId(), projectId);
        return Result.ok(eventStatuses);
    }
}