package com.mbp.eng.framework.common.util.sql;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class DataKey {
    int id;
    String dataKey;
    String eid;
    String eidName;
    String creator;
    String appKey;
    int appType; // 0 服务端, 1 为客户端, 2 为binlog
    int tableType;
    String createTime;
    String updateTime;
    String addr;
    String database;
    String tablename;
    String hiveDBCname;
    String note;
    int ddlType;
    List<Columns> columns;

    @Data
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Columns {
        public int id;
        public String name;
        public String type;
        public String note;
        public int ddlType;
        public int order;
        public int primary = 0;
        public int pid = 0;
    }
}
