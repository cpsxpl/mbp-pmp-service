package com.mbp.eng.framework.domain.base;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.Transient;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * 基础查询类
 */
class BaseQuery implements Serializable {
    private static final long serialVersionUID = 1L;

    @JsonIgnore
    @Transient
    private transient Integer startIndex; // 开始索引

    @JsonIgnore
    @Transient
    private transient Integer endIndex; // 结束索引

    @JsonIgnore
    @Transient
    private transient String orderField; // 排序字段

    @JsonIgnore
    @Transient
    private transient String orderFieldType; // 排序字段类型

    @JsonIgnore
    @Transient
    private transient Map<String, Object> queryData; // 查询扩展

    @JsonIgnore
    @Transient
    private transient String keyword; // 关键则查询

    public Integer getStartIndex() {
        return startIndex;
    }

    public void setStartIndex(Integer startIndex) {
        this.startIndex = startIndex;
    }

    public Integer getEndIndex() {
        return endIndex;
    }

    public void setEndIndex(Integer endIndex) {
        this.endIndex = endIndex;
    }

    //每页显示条数
    @JsonIgnore
    public Integer getPageSize() {
        if (endIndex != null && startIndex != null) {
            return endIndex - startIndex;
        }
        return null;
    }

    public String getOrderField() {
        return orderField;
    }

    public void setOrderField(String orderField) {
        this.orderField = orderField;
    }

    @JsonIgnore
    public String getOrderFieldType() {
        if ("DESC".equalsIgnoreCase(orderFieldType) || "ASC".equalsIgnoreCase(orderFieldType)) {
            return orderFieldType.toUpperCase();
        }
        return null;
    }

    @JsonIgnore
    public String getOrderFieldNextType() {
        if ("ASC".equalsIgnoreCase(orderFieldType)) {
            return "DESC";
        }
        return "ASC";
    }

    @JsonIgnore
    public void setOrderFieldType(String orderFieldType) {
        this.orderFieldType = orderFieldType;
    }

    public Map<String, Object> getQueryData() {
        if (queryData != null && queryData.size() > 0) {
            return queryData;
        }
        return null;
    }

    //添加其它查询数据
    public void addQueryData(String key, Object value) {
        if (queryData == null) {
            queryData = new HashMap<String, Object>();
        }
        queryData.put(key, value);
    }

    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }

    public String getkeyword() {
        return keyword;
    }
}
