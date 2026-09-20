package com.mbp.pmp.module.work.controller;

import com.kakarote.common.result.Result;
import com.mbp.pmp.module.work.domain.Project;
import com.mbp.pmp.module.work.domain.ProjectGroupManagement;
import com.mbp.pmp.module.work.service.IProjectGroupManagementService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * <p>
 * 项目分组管理表 前端控制器
 * </p>
 *
 * @author cpsxpl
 * @since 2022-09-17
 */
@RestController
@RequestMapping("/projectGroupManagement")
@Api(tags = "项目分组管理new")
public class ProjectGroupManagementController {
    @Autowired
    IProjectGroupManagementService projectGroupManagementService;

    @ApiOperation("移动/增加项目到分组")
    @RequestMapping(value = "/moveToGroup", method = RequestMethod.POST)
    public Result moveToGroup(@RequestBody ProjectGroupManagement projectGroupManagement) {
        projectGroupManagementService.moveToGroup(projectGroupManagement);
        return Result.ok();
    }

    @ApiOperation("移除项目分组")
    @RequestMapping(value = "/removeToGroup", method = RequestMethod.POST)
    public Result removeToGroup(@RequestParam("groupId") Long groupId, @RequestParam("projectId") Long projectId) {
        projectGroupManagementService.removeToGroup(groupId, projectId);
        return Result.ok();
    }

    @PostMapping("/searchProjectGroupList")
    @ApiOperation("查询当前分组下的项目")
    public Result<List<Project>> searchProjectGroupList(@RequestParam Long groupId) {
        return Result.ok(projectGroupManagementService.searchProjectGroupList(groupId));
    }
}