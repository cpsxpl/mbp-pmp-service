package com.mbp.eng.module.work.common.project;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author cpsxpl
 */
@Data
@ApiModel
public class RemoveWorkOwnerUserBO {
    @ApiModelProperty("项目id")
    private Long workId;

    @ApiModelProperty("员工id")
    private Long ownerUserId;
}
