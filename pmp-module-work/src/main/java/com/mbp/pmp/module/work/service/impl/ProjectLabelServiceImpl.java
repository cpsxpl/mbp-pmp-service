package com.mbp.pmp.module.work.service.impl;

import cn.hutool.core.util.StrUtil;
import com.kakarote.common.servlet.BaseServiceImpl;
import com.kakarote.common.utils.UserUtil;
import com.mbp.pmp.module.work.entity.PO.ProjectLabel;
import com.mbp.pmp.module.work.mapper.ProjectLabelMapper;
import com.mbp.pmp.module.work.service.IProjectLabelService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 任务标签表 服务实现类
 * </p>
 *
 * @author cpsxpl
 * @since 2022-09-20
 */
@Service
public class ProjectLabelServiceImpl extends BaseServiceImpl<ProjectLabelMapper, ProjectLabel> implements IProjectLabelService {
    @Override
    public void add(ProjectLabel ProjectLabel) {
        ProjectLabel.setCreateUserId(UserUtil.getUserId());
        save(ProjectLabel);
    }

    @Override
    public void update(ProjectLabel ProjectLabel) {
        ProjectLabel.setUpdateUserId(UserUtil.getUserId());
        this.baseMapper.updateById(ProjectLabel);
    }

    @Override
    public void delete(Long id) {
        removeById(id);
    }

    @Override
    public List<ProjectLabel> queryTaskLabelList(String ids) {
        return lambdaQuery().in(ProjectLabel::getLabelId, StrUtil.split(ids, ",")).list();
    }

    @Override
    public List<ProjectLabel> queryList(String name) {
        return lambdaQuery().like(ProjectLabel::getName, name).list();
    }
}