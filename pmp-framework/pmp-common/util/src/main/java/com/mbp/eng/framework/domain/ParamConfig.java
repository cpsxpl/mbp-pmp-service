package com.mbp.eng.framework.domain;

import com.mbp.eng.framework.domain.base.BaseDomain;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class ParamConfig extends BaseDomain {
    private String url;
    private String user;
    private String password;
}
