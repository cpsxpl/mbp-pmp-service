package com.mbp.pmp.module.work.service.wk.impl;

import cn.hutool.core.util.ObjectUtil;
import com.kakarote.common.result.BasePage;
import com.kakarote.common.servlet.BaseServiceImpl;
import com.kakarote.ids.provider.utils.UserCacheUtil;
import com.mbp.pmp.module.work.dao.mybatis.mapper.baseMapper.wk.ProjectTaskFollowsMapper;
import com.mbp.pmp.module.work.domain.entity.bo.ProjectTaskFollowsQueryBO;
import com.mbp.pmp.module.work.domain.entity.vo.ProjectTaskFollowsVO;
import com.mbp.pmp.module.work.domain.model.wk.ProjectTaskFollows;
import com.mbp.pmp.module.work.service.wk.IProjectTaskFollowsService;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 项目事项跟进记录/客户动态表 服务实现类
 * </p>
 *
 * @author cpsxpl
 * @since 2022-09-21
 */
@Service
public class ProjectTaskFollowsServiceImpl extends BaseServiceImpl<ProjectTaskFollowsMapper, ProjectTaskFollows> implements IProjectTaskFollowsService {
    @Override
    public BasePage<ProjectTaskFollowsVO> getProjectFollowsPageList(ProjectTaskFollowsQueryBO projectTaskFollowsQueryBO) {
        BasePage<ProjectTaskFollowsVO> followsVOBasePage = baseMapper.getProjectFollowsPageList(projectTaskFollowsQueryBO.parse(), projectTaskFollowsQueryBO);
        followsVOBasePage.getList().forEach(f -> {
            f.setUser(ObjectUtil.isNotNull(f.getUserId()) ? UserCacheUtil.getUserInfo(f.getUserId()) : null);
        });
        // projectTask.setMainUserName(ObjectUtil.isNotNull(projectTask.getMainUserId()) ? UserCacheUtil.getUserInfo(projectTask.getMainUserId()).getRealname() : null);
        return followsVOBasePage;
    }
}