package com.mbp.pmp.module.work.controller;

import com.kakarote.common.result.Result;
import com.mbp.pmp.module.work.common.project.ProjectFieldSortAddBO;
import com.mbp.pmp.module.work.common.project.ProjectFieldSortQueryBO;
import com.mbp.pmp.module.work.domain.ProjectFieldSort;
import com.mbp.pmp.module.work.service.IProjectFieldSortService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * <p>
 * 字段排序表 前端控制器
 * </p>
 *
 * @author cpsxpl
 * @since 2022-09-16
 */
@RestController
@RequestMapping("/projectFieldSort")
public class ProjectFieldSortController {
    @Autowired
    private IProjectFieldSortService projectFieldSortService;

    @ApiOperation("查询字自定义字段段列表")
    @RequestMapping(value = "/list", method = RequestMethod.POST)
    public Result<List<ProjectFieldSort>> selectUserSort(@RequestBody ProjectFieldSortQueryBO projectFieldSortBO) {
        return Result.ok(projectFieldSortService.selectUserSort(projectFieldSortBO));
    }

    @ApiOperation("设置个人字段排序")
    @RequestMapping(value = "/addOrUpdate", method = RequestMethod.POST)
    public Result<Boolean> addOrUpdateUserSort(@RequestBody ProjectFieldSortAddBO projectFieldSortAddBO) {
        return Result.ok(projectFieldSortService.addOrUpdateUserSort(projectFieldSortAddBO));
    }
}