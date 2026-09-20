package com.mbp.pmp.module.work.controller;

import com.kakarote.common.result.BasePage;
import com.kakarote.common.result.Result;
import com.mbp.pmp.module.work.entity.BO.ProjectQueryBO;
import com.mbp.pmp.module.work.domain.Project;
import com.mbp.pmp.module.work.service.IProjectCollectService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.NotNull;

/**
 * <p>
 * 项目收藏表 前端控制器
 * </p>
 *
 * @author cpsxpl
 * @since 2022-09-08
 */
@RestController
@RequestMapping("/projectCollect")
@Api(tags = "项目收藏new")
public class ProjectCollectController {
    @Autowired
    private IProjectCollectService projectCollectService;

    @PostMapping("/collect/{projectId}")
    @ApiOperation("收藏项目/取消收藏")
    public Result collect(@PathVariable @NotNull Long projectId) {
        projectCollectService.collect(projectId);
        return Result.ok();
    }

    @PostMapping("/queryCollect/{projectId}")
    @ApiOperation("查询收藏项目")
    public Result queryCollectByProjectId(@PathVariable @NotNull Long projectId) {
        projectCollectService.queryCollectByProjectId(projectId);
        return Result.ok();
    }

    @PostMapping("/myCollectByProjectList")
    @ApiOperation("我的收藏项目列表")
    public Result<BasePage<Project>> myCollectByProjectList(@RequestBody ProjectQueryBO projectQueryBO) {
        BasePage<Project> basePage = projectCollectService.myCollectByProjectList(projectQueryBO);
        return Result.ok(basePage);
    }
}