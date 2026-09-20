package com.kakarote.work.service;

import com.kakarote.common.servlet.BaseService;
import com.kakarote.work.entity.PO.ProjectTaskComment;

import java.util.List;

/**
 * <p>
 * 任务评论表 服务类
 * </p>
 *
 * @author cpsxpl
 * @since 2020-05-18
 */
public interface IProjectTaskCommentService extends BaseService<ProjectTaskComment> {
    /**
     * 查询评论列表
     *
     * @param typeId typeId
     * @param type   type
     * @return data
     */
    List<ProjectTaskComment> queryCommentList(Long typeId);

    /**
     * 新增评论
     *
     * @param taskComment taskComment
     */
    void setComment(ProjectTaskComment taskComment);
}