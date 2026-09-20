package com.kakarote.work.constant;

import java.util.Objects;

/**
 * @author cpsxpl
 * 优先级类型
 */

public enum PriorityEnum {
    //缺陷类型
    NONE(0, "无"),
    LOW(1, "低"),
    MID(2, "中"),
    HIGH(3, "高");

    PriorityEnum(Integer type, String desc) {
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

    public static PriorityEnum enumByType(Integer type) {
        for (PriorityEnum priorityEnum : values()) {
            if (Objects.equals(type, priorityEnum.getType())) {
                return priorityEnum;
            }
        }
        return NONE;
    }

    public static PriorityEnum enumByDesc(String desc) {
        for (PriorityEnum priorityEnum : values()) {
            if (Objects.equals(desc, priorityEnum.getDesc())) {
                return priorityEnum;
            }
        }
        return NONE;
    }
}
