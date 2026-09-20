package com.mbp.pmp.module.work.constant;

import java.util.Objects;

/**
 * @author cpsxpl
 * 初始化状态
 */

public enum InitStatusTypeEnum {
    //缺陷类型
    NONE(1, "未开始"),
    RUNNING(2, "进行中"),
    END(3, "已完成");

    InitStatusTypeEnum(Integer type, String desc) {
        this.type = type;
        this.desc = desc;
    }

    private final Integer type;
    private final String desc;

    public Integer getType() {
        return type;
    }

    public String getDesc() {
        return desc;
    }

    public static InitStatusTypeEnum enumByType(Integer type) {
        for (InitStatusTypeEnum initStatusTypeEnum : values()) {
            if (Objects.equals(type, initStatusTypeEnum.getType())) {
                return initStatusTypeEnum;
            }
        }
        return null;
    }

    public static InitStatusTypeEnum enumByDesc(String desc) {
        for (InitStatusTypeEnum initStatusTypeEnum : values()) {
            if (Objects.equals(desc, initStatusTypeEnum.getDesc())) {
                return initStatusTypeEnum;
            }
        }
        return null;
    }
}