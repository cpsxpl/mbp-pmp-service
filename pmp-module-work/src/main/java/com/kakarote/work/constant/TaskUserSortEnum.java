package com.kakarote.work.constant;

import java.util.Objects;

/**
 * @author cpsxpl
 * 任务排序类型
 */

public enum TaskUserSortEnum {
    //个人排序类型
    TOPLAN(0, "待规划");

    TaskUserSortEnum(Integer type, String desc) {
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

    public static TaskUserSortEnum enumByType(Integer type) {
        for (TaskUserSortEnum taskUserSortEnum : values()) {
            if (Objects.equals(type, taskUserSortEnum.getType())) {
                return taskUserSortEnum;
            }
        }
        return null;
    }

    public static TaskUserSortEnum enumByDesc(String desc) {
        for (TaskUserSortEnum taskUserSortEnum : values()) {
            if (Objects.equals(desc, taskUserSortEnum.getDesc())) {
                return taskUserSortEnum;
            }
        }
        return null;
    }
}
