package com.mbp.pmp.module.work.common.project;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author cpsxpl
 */
@Data
@ApiModel("项目任务排序")
public class TaskUserSortBO {
    @ApiModelProperty("任务id")
    private Long taskId;

    @ApiModelProperty("排序值")
    private Long sortNum;
}
