package com.mbp.eng.module.work.common.project;

import com.kakarote.common.upload.entity.UploadEntity;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * @author cpsxpl
 * @since : 2023/1/9
 */
@Getter
@Setter
public class FileUploadResultVO extends UploadEntity {
    @ApiModelProperty(value = "批次id")
    private String batchId;
}