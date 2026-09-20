package com.mbp.pmp.module.work.controller;

import com.kakarote.common.result.Result;
import com.mbp.pmp.module.work.entity.BO.DeleteProjectAnnouncementBO;
import com.mbp.pmp.module.work.entity.BO.ProjectAnnouncementBO;
import com.mbp.pmp.module.work.entity.VO.ProjectAnnouncementVO;
import com.mbp.pmp.module.work.service.IProjectAnnouncementService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 项目公告表 前端控制器
 *
 * @author cpsxpl
 * @since 2022-09-09
 */
@RestController
@RequestMapping("/projectAnnouncement")
@Api(tags = "项目公告new")
public class ProjectAnnouncementController {
    @Autowired
    private IProjectAnnouncementService projectAnnouncementService;

    @PostMapping("/addProjectAnnouncement")
    @ApiOperation("添加项目公告")
    public Result addProjectAnnouncement(@RequestBody ProjectAnnouncementBO projectAnnouncementBO) {
        projectAnnouncementService.addProjectAnnouncement(projectAnnouncementBO);
        return Result.ok();
    }

    @PostMapping("/setProjectAnnouncement")
    @ApiOperation("修改项目公告")
    public Result setProjectAnnouncement(@RequestBody ProjectAnnouncementBO projectAnnouncementBO) {
        projectAnnouncementService.setProjectAnnouncement(projectAnnouncementBO);
        return Result.ok();
    }

    @PostMapping("/delProjectAnnouncement")
    @ApiOperation("删除项目公告")
    public Result delProjectAnnouncement(@RequestBody @Validated DeleteProjectAnnouncementBO deleteProjectAnnouncementBO) {
        projectAnnouncementService.delProjectAnnouncement(deleteProjectAnnouncementBO);
        return Result.ok();
    }

    @PostMapping("/getProjectAnnouncement")
    @ApiOperation("查询项目公告")
    public Result<List<ProjectAnnouncementVO>> getProjectAnnouncement(@RequestBody ProjectAnnouncementBO projectAnnouncementBO) {
        return Result.ok(projectAnnouncementService.getProjectAnnouncement(projectAnnouncementBO));
    }
}