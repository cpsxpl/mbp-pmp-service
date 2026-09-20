package com.mbp.eng.module.work.common.project;

import com.mbp.eng.module.work.entity.BO.ProjectBoardStatusBO;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

@Data
public class ProjectBoardBO {
    @ApiModelProperty(value = "看板名称")
    public String boardName;
    @ApiModelProperty(value = "排序 从小到大")
    private Integer sorting;
    @ApiModelProperty("状态列表")
    List<ProjectBoardStatusBO> boardStatusBOList;
}
