package com.mbp.eng.module.work.constant;

import java.util.Objects;

/**
 * @author cpsxpl
 * 缺陷类型
 */

public enum WrongTypeEnum {
    //缺陷类型
    FUNCTION(1, "功能缺陷"),
    UI(2, "UI界面问题"),
    EASE_OF_USE(3, "易用性问题"),
    SECURITY(4, "安全问题"),
    PERFORMANCE(5, "性能问题"),
    //已读
    CODE(6, "代码错误");

    WrongTypeEnum(Integer type, String desc) {
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

    public static WrongTypeEnum enumByType(Integer type) {
        for (WrongTypeEnum wrongTypeEnum : values()) {
            if (Objects.equals(type, wrongTypeEnum.getType())) {
                return wrongTypeEnum;
            }
        }
        return null;
    }

    public static WrongTypeEnum enumByDesc(String desc) {
        for (WrongTypeEnum wrongTypeEnum : values()) {
            if (Objects.equals(desc, wrongTypeEnum.getDesc())) {
                return wrongTypeEnum;
            }
        }
        return null;
    }
}