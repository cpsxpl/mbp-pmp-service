package com.mbp.eng.module.work.entity.BO;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * @author cpsxpl
 */
@Data
@ApiModel("开始迭代保存对象")
public class StartBelongIterationBO {
    @ApiModelProperty("任务id")
    private Long taskId;

    @ApiModelProperty("开始时间")
    private LocalDateTime startTime;

    @ApiModelProperty("结束时间")
    private LocalDateTime stopTime;
}
