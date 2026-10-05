package com.mbp.pmp.module.work.common.project;

import com.mbp.pmp.module.work.domain.entity.bo.ProjectBoardBO;
import lombok.Data;

import java.util.List;

@Data
public class ResetBoardBO {
    private List<ProjectBoardBO> boardBOS;
    private Long schemeRelationId;
    private Long projectId;
    private Integer taskType;
}
