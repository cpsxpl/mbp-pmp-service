package com.mbp.eng.framework.domain.base;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.Transient;

import java.util.Date;

@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class BaseDomain extends BaseQuery {
    private static final long serialVersionUID = 1L;

    @JsonIgnore
    @Transient
    private Integer baseId; // 编号

    @JsonIgnore
    @Transient
    private String uuid; // 唯一编号

    @JsonIgnore
    @Transient
    private String code; // 编码

    @JsonIgnore
    @Transient
    private String remark; // 备注

    @JsonIgnore
    @Transient
    private Date createDate; // 创建日期

    @JsonIgnore
    @Transient
    private String createUser; // 创建者

    @JsonIgnore
    @Transient
    private Date modifyDate; // 最后修改日期

    @JsonIgnore
    @Transient
    private String modifyUser; // 最后修改者

    @JsonIgnore
    @Transient
    private Integer isDel; // 是否删除

    @JsonIgnore
    @Transient
    private String createUserErp; //创建者erp

    @JsonIgnore
    @Transient
    private String modifyUserErp; //更新者erp
}
