package com.mbp.eng.framework.common.util.sql;

import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.Pair;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DataKeyTemplate {
    private static Logger logger = LoggerFactory.getLogger(DataKeyTemplate.class);

    public static Pair<String, String> createClientChannel() {
        String createTableSql = createClientTableTemplate;
        String createSchedulerSql = createClientParquetSchedulerTemplate;
        Pair<String, String> pair = new ImmutablePair<>(createTableSql, createSchedulerSql);
        return pair;
    }

    public static final String createBinlogNoPartitionTableTemplate = "CREATE EXTERNAL TABLE  ${hive_database_name}.db_${database}_${tablename}_full (\n" +
            "tablename string COMMENT 'mysql表名',\n" +
            "sql_type string COMMENT '最后一次操作类型',\n" +
            "record_id string COMMENT '同步位点',\n" +
            "binlog_time string COMMENT 'binlog时间',\n" +
            "unique_key string COMMENT '主键'," +
            "${columns}\n" +
            ")\n" +
            "COMMENT '${db_comment}'\n" +
            "ROW FORMAT SERDE\n" +
            "'org.apache.hadoop.hive.ql.io.parquet.serde.ParquetHiveSerDe'\n" +
            "STORED AS INPUTFORMAT\n" +
            "'org.apache.hadoop.hive.ql.io.parquet.MapredParquetInputFormat'\n" +
            "OUTPUTFORMAT\n" +
            "'org.apache.hadoop.hive.ql.io.parquet.MapredParquetOutputFormat';";

    public static final String createBinlogPartitionTableTemplate = "CREATE EXTERNAL TABLE ${hive_database_name}.db_${database}_${tablename}_incp (\n" +
            "tablename string COMMENT 'mysql表名',\n" +
            "sql_type string COMMENT '最后一次操作类型',\n" +
            "record_id string COMMENT '同步位点',\n" +
            "binlog_time string COMMENT 'binlog时间',\n" +
            "unique_key string COMMENT '主键',\n" +
            "${columns}\n" +
            ")\n" +
            "COMMENT '${db_comment}' partitioned by (ymd string comment '更新日期分区')\n" +
            "ROW FORMAT SERDE\n" +
            "'org.apache.hadoop.hive.ql.io.parquet.serde.ParquetHiveSerDe'\n" +
            "STORED AS INPUTFORMAT\n" +
            "'org.apache.hadoop.hive.ql.io.parquet.MapredParquetInputFormat'\n" +
            "OUTPUTFORMAT\n" +
            "'org.apache.hadoop.hive.ql.io.parquet.MapredParquetOutputFormat';";

    public static final String createBinlogFullPartitionTableTemplate = "CREATE EXTERNAL TABLE ${hive_database_name}.db_${database}_${tablename}_fullp (\n" +
            "tablename string COMMENT 'mysql表名',\n" +
            "sql_type string COMMENT '最后一次操作类型',\n" +
            "record_id string COMMENT '同步位点',\n" +
            "binlog_time string COMMENT 'binlog时间',\n" +
            "unique_key string COMMENT '主键',\n" +
            "${columns}\n" +
            ")\n" +
            "COMMENT '${db_comment}' partitioned by (ymd string comment '备份日期')\n" +
            "ROW FORMAT SERDE\n" +
            "'org.apache.hadoop.hive.ql.io.parquet.serde.ParquetHiveSerDe'\n" +
            "STORED AS INPUTFORMAT\n" +
            "'org.apache.hadoop.hive.ql.io.parquet.MapredParquetInputFormat'\n" +
            "OUTPUTFORMAT\n" +
            "'org.apache.hadoop.hive.ql.io.parquet.MapredParquetOutputFormat';";

    public static final String createBinlogNoPartitionParquetSchedulerTemplate = "insert overwrite table ${hive_database_name}.db_${database}_${tablename}_full\n" +
            "select tablename, sql_type, record_id, binlog_time, unique_key, ${columns}\n" +
            "from\n" +
            "(select tmp.*,\n" +
            "row_number() over(partition by unique_key \n" +
            "order by binlog_time desc,\n" +
            "length(split(record_id,':')[0]) desc, split(record_id,':')[0] desc,\n" +
            "length(split(record_id,':')[1]) desc, split(record_id,':')[1] desc) as rowid\n" +
            "from\n" +
            "(select * from ${hive_database_name}.db_${database}_${tablename}_full\n" +
            "union all\n" +
            "select str['table'] as tablename,\n" +
            "str['sql_type'] as sql_type,\n" +
            "str['record_id'] as record_id,\n" +
            "str['time'] as binlog_time,\n" +
            "concat(str['table'], ${get_json_object_parquet_primary}) as unique_key,\n" +
            "${get_json_object_parquet}\n" +
            "from ${appname}logs.${appname}_binlog\n" +
            "where ymd='${last_1_day_int}'\n" +
            "and eid='${database}.${tablename}'\n" +
            "and str['sql_type'] in ('UPDATE','INSERT','DELETE','DUMP')\n" +
            ") tmp\n" +
            ") t\n" +
            "where rowid=1\n" +
            ";";

    public static final String createBinlogPartitionParquetSchedulerTemplate = "insert overwrite table ${hive_database_name}.db_${database}_${tablename}_incp partition (ymd='${last_1_day_int}')\n" +
            "select tablename, sql_type, record_id, binlog_time, unique_key, ${columns}\n" +
            "from\n" +
            "(select t.*,\n" +
            "row_number() over(partition by unique_key \n" +
            "order by binlog_time desc,\n" +
            "length(split(record_id,':')[0]) desc,split(record_id,':')[0] desc,\n" +
            "length(split(record_id,':')[1]) desc,split(record_id,':')[1] desc) rowid\n" +
            "from\n" +
            "(select str['table'] as tablename,\n" +
            "str['sql_type'] as sql_type,\n" +
            "str['record_id'] as record_id,\n" +
            "str['time'] as binlog_time,\n" +
            "concat(str['table'], ${get_json_object_parquet_primary} ) as unique_key, \n" +
            "${get_json_object_parquet}\n" +
            "from ${appname}logs.${appname}_binlog\n" +
            "where ymd in ('${last_1_day_int}', '${last_0_day_int}')\n" +
            "and eid='${database}.${tablename}'\n" +
            "and str['sql_type'] in ('UPDATE','INSERT','DELETE','DUMP')\n" +
            ") t\n" +
            ") tt\n" +
            "where rowid=1;";

    public static final String createBinlogFullPartitionParquetSchedulerTemplate = "insert overwrite table ${hive_database_name}.db_${database}_${tablename}_fullp partition (ymd='${last_1_day_int}')\n" +
            "select tablename, sql_type, record_id, binlog_time, unique_key, ${columns}\n" +
            "from\n" +
            "(select tmp.*,\n" +
            "row_number() over(partition by unique_key \n" +
            "order by binlog_time desc,\n" +
            "length(split(record_id,':')[0]) desc, split(record_id,':')[0] desc,\n" +
            "length(split(record_id,':')[1]) desc, split(record_id,':')[1] desc) as rowid\n" +
            "from\n" +
            "(select tablename, sql_type, record_id, binlog_time, unique_key, ${columns} from ${hive_database_name}.db_${database}_${tablename}_fullp where ymd='${last_2_day_int}'\n" +
            "union all\n" +
            "select str['table'] as tablename,\n" +
            "str['sql_type'] as sql_type,\n" +
            "str['record_id'] as record_id,\n" +
            "str['time'] as binlog_time,\n" +
            "concat(str['table'],  ${get_json_object_parquet_primary}) as unique_key, -- xxx时同步时定义的主键字段名,可能不止一个字段\n" +
            "${get_json_object_parquet}\n" +
            "from ${appname}logs.${appname}_binlog\n" +
            "where ymd='${last_1_day_int}'\n" +
            "and eid='${database}.${tablename}'\n" +
            "and str['sql_type'] in ('UPDATE','INSERT','DELETE','DUMP')\n" +
            ") tmp\n" +
            ") t\n" +
            "where rowid=1\n" +
            ";";

    // columns 去除逗号
    public static final String createServerTableTemplate = "create table ${hive_database_name}.${hiveTableName} (\n" +
            "data_key string COMMENT 'datakey',\n" +
            "service_name string COMMENT '服务发现名',\n" +
            "event_time string COMMENT '事件时间',\n" +
            "atom string COMMENT '原子信息json',\n" +
            "${columns}\n" +
            ") comment '${eid}' partitioned by (ymd string COMMENT '日期分区') \n" +
            "ROW FORMAT SERDE 'org.apache.hadoop.hive.ql.io.parquet.serde.ParquetHiveSerDe' \n" +
            "STORED AS INPUTFORMAT 'org.apache.hadoop.hive.ql.io.parquet.MapredParquetInputFormat' \n" +
            "OUTPUTFORMAT 'org.apache.hadoop.hive.ql.io.parquet.MapredParquetOutputFormat'\n" +
            ";";

    public static final String createServerParquetSchedulerTemplate = "insert overwrite table ${hive_database_name}.serverlog_${eid} partition (ymd='${last_1_day_int}')\n" +
            "select str['data_key'] as data_key,\n" +
            "       str['service_name'] as service_name,\n" +
            "       str['time'] as event_time,\n" +
            "       str['atom'] as atom,\n" +
            "       ${get_json_object_parquet}\n" +
            "from (select str from ${appname}logs.${appname}_serverlog where eid='${eid}' and ymd='${last_1_day_int}' group by str) t;";

    // columns 去除逗号
    public static final String createClientTableTemplate = "create table ${hive_database_name}.${hiveTableName} (\n" +
            "uid bigint COMMENT '用户ID',\n" +
            "md_mod int COMMENT '是否前端上报0否1是',\n" +
            "md_session string COMMENT '用户访问session,前后台切换会更新',\n" +
            "client_ts bigint COMMENT '客户端时间戳,13位',\n" +
            "client_time string COMMENT '客户端时间',\n" +
            "record_ts bigint COMMENT '服务端时间戳,13位',,\n" +
            "record_time string COMMENT '服务端时间',\n" +
            "lc string COMMENT 'lc',\n" +
            "cv string COMMENT '客户端版本',\n" +
            "cc string COMMENT '渠道',\n" +
            "ua string COMMENT '客户端代理号',\n" +
            "devi string COMMENT '设备号',\n" +
            "imsi string COMMENT '手机卡标识imsi',\n" +
            "imei string COMMENT '手机串号imei',\n" +
            "oaid string COMMENT 'oaid',\n" +
            "idfa string COMMENT 'ios设备idfa',\n" +
            "idfv string COMMENT 'ios设备idfv',\n" +
            "ndid string COMMENT '数美ID',\n" +
            "aid string COMMENT '安卓手机广告码',\n" +
            "conn string COMMENT '网络连接类型',\n" +
            "osversion string COMMENT '手机系统版本',\n" +
            "ip string COMMENT 'ip',\n" +
            "logid string COMMENT 'ABtest标志',\n" +
            "ropklv int COMMENT '是否越狱1是0否',\n" +
            "latitude string COMMENT '纬度',\n" +
            "longitude string COMMENT '经度',\n" +
            "path string COMMENT '路径',\n" +
            "cpu string COMMENT 'cpu',\n" +
            "${columns}\n" +
            ") comment '${eid}' partitioned by (ymd string COMMENT '日期分区') \n" +
            "ROW FORMAT SERDE 'org.apache.hadoop.hive.ql.io.parquet.serde.ParquetHiveSerDe' \n" +
            "STORED AS INPUTFORMAT 'org.apache.hadoop.hive.ql.io.parquet.MapredParquetInputFormat' \n" +
            "OUTPUTFORMAT 'org.apache.hadoop.hive.ql.io.parquet.MapredParquetOutputFormat'\n" +
            ";";

    public static final String createClientParquetSchedulerTemplate = "insert overwrite table ${hive_database_name}.applog_${eid} partition (ymd='${last_1_day_int}')\n" +
            "select case when cast(str['md_userid'] as bigint)>0 and cast(str['md_userid'] as bigint)<4300000000 then cast(str['md_userid'] as bigint) else 0 end as uid,\n" +
            "cast(str['md_mod'] as int) as md_mod,\n" +
            "str['md_session'] as md_session,\n" +
            "cast(str['md_etime'] as bigint) as client_ts,\n" +
            "from_unixtime(cast(substr(str['md_etime'],1,10) as bigint)) as client_time,\n" +
            "cast(str['record_time'] as bigint) as record_ts,\n" +
            "from_unixtime(cast(substr(str['record_time'],1,10) as bigint)) as record_time,\n" +
            "       str['lc'] as lc,\n" +
            "       str['cv'] as cv,\n" +
            "       str['cc'] as cc,\n" +
            "       str['ua'] as ua,\n" +
            "       str['devi'] as devi,\n" +
            "       str['imsi'] as imsi,\n" +
            "       str['imei'] as imei,\n" +
            "       str['oaid'] as oaid,\n" +
            "       inke_smid_udf(str['smid']) as smid,\n" +
            "       str['idfa'] as idfa,\n" +
            "       str['idfv'] as idfv,\n" +
            "       str['ndid'] as ndid,\n" +
            "       str['aid'] as aid,\n" +
            "       str['conn'] as conn,\n" +
            "       str['osversion'] as osversion,\n" +
            "       str['client_ip'] as ip,\n" +
            "       case when str['md_userid']=str['uid'] then nvl(str['md_logid'], str['logid']) else str['md_logid'] end as logid,\n" +
            "       nvl(cast(str['jb'] as int),0) as ropklv,\n" +
            "       str['latitude'] as latitude,\n" +
            "       str['longitude'] as longitude,\n" +
            "       str['md_path'] as path,\n" +
            "       str['cpu'] as cpu,\n" +
            "       ${get_json_object_parquet}\n" +
            "from (select str from ${appname}logs.${appname}_serverlog where eid='${eid}' and ymd='${last_1_day_int}' group by str) t\n" +
            ";";

    public static final String createClientParquetSchedulerTemplateJson = "insert overwrite table ${hive_database_name}.applog_${eid} partition (ymd='${last_1_day_int}')\n" +
            "select case when cast(get_json_object(str, '$.md_userid') as bigint)>0 and cast(get_json_object(str, '$.md_userid') as bigint)<4300000000 " +
            "then cast(get_json_object(str, '$.md_userid') as bigint) " +
            "else 0 end as uid,\n" +
            "cast(get_json_object(str, '$.md_mod') as int) as md_mod,\n" +
            "get_json_object(str, '$.md_session') as md_session,\n" +
            "cast(get_json_object(str, '$.md_etime') as bigint) as client_ts,\n" +
            "from_unixtime(cast(substr(get_json_object(str, '$.md_etime'),1,10) as bigint)) as client_time,\n" +
            "cast(get_json_object(str, '$.record_time') as bigint) as record_ts,\n" +
            "from_unixtime(cast(substr(get_json_object(str, '$.record_time'),1,10) as bigint)) as record_time,\n" +
            "       get_json_object(str, '$.lc') as lc,\n" +
            "       get_json_object(str, '$.cv') as cv,\n" +
            "       get_json_object(str, '$.cc') as cc,\n" +
            "       get_json_object(str, '$.ua') as ua,\n" +
            "       get_json_object(str, '$.devi') as devi,\n" +
            "       get_json_object(str, '$.imsi') as imsi,\n" +
            "       get_json_object(str, '$.imei') as imei,\n" +
            "       get_json_object(str, '$.oaid') as oaid,\n" +
            "       inke_smid_udf(get_json_object(str, '$.smid')) as smid,\n" +
            "       get_json_object(str, '$.idfa') as idfa,\n" +
            "       get_json_object(str, '$.idfv') as idfv,\n" +
            "       get_json_object(str,'$.ndid') as ndid,\n" +
            "       get_json_object(str, '$.aid') as aid,\n" +
            "       get_json_object(str, '$.conn') as conn,\n" +
            "       get_json_object(str, '$.osversion') as osversion,\n" +
            "       get_json_object(str, '$.client_ip') as ip,\n" +
            "       case when get_json_object(str, '$.md_userid')=get_json_object(str, '$.uid') then nvl(get_json_object(str, '$.md_logid'),get_json_object(str, '$.logid')) " +
            "       else get_json_object(str, '$.md_logid') end as logid,\n" +
            "       nvl(cast(get_json_object(str, '$.jb') as int),0) as ropklv,\n" +
            "       get_json_object(str, '$.latitude') as latitude,\n" +
            "       get_json_object(str, '$.longitude') as longitude,\n" +
            "       get_json_object(str, '$.md_path') as path,\n" +
            "       get_json_object(str, '$.cpu') as cpu,\n" +
            "       ${get_json_object_parquet} \n" +
            "from (select str from ${appname}logs.${appname}_serverlog where eid='${eid}' and ymd='${last_1_day_int}' group by str) t\n" +
            ";";

    // columns 去除逗号
    public static final String createClientTableTemplateNullColumns = "create table ${hive_database_name}.${hiveTableName} (\n" +
            "uid bigint COMMENT '用户ID',\n" +
            "md_mod int COMMENT '是否前端上报0否1是',\n" +
            "md_session string COMMENT '用户访问session,前后台切换会更新',\n" +
            "client_ts bigint COMMENT '客户端时间戳,13位',\n" +
            "client_time string COMMENT '客户端时间',\n" +
            "record_ts bigint COMMENT '服务端时间戳,13位',,\n" +
            "record_time string COMMENT '服务端时间',\n" +
            "lc string COMMENT 'lc',\n" +
            "cv string COMMENT '客户端版本',\n" +
            "cc string COMMENT '渠道',\n" +
            "ua string COMMENT '客户端代理号',\n" +
            "devi string COMMENT '设备号',\n" +
            "imsi string COMMENT '手机卡标识imsi',\n" +
            "imei string COMMENT '手机串号imei',\n" +
            "oaid string COMMENT 'oaid',\n" +
            "idfa string COMMENT 'ios设备idfa',\n" +
            "idfv string COMMENT 'ios设备idfv',\n" +
            "ndid string COMMENT '数美ID',\n" +
            "aid string COMMENT '安卓手机广告码',\n" +
            "conn string COMMENT '网络连接类型',\n" +
            "osversion string COMMENT '手机系统版本',\n" +
            "ip string COMMENT 'ip',\n" +
            "logid string COMMENT 'ABtest标志',\n" +
            "ropklv int COMMENT '是否越狱1是0否',\n" +
            "latitude string COMMENT '纬度',\n" +
            "longitude string COMMENT '经度',\n" +
            "path string COMMENT '路径',\n" +
            "cpu string COMMENT 'cpu',\n" +
            ") comment '${eid}' partitioned by (ymd string COMMENT '日期分区') \n" +
            "ROW FORMAT SERDE 'org.apache.hadoop.hive.ql.io.parquet.serde.ParquetHiveSerDe' \n" +
            "STORED AS INPUTFORMAT 'org.apache.hadoop.hive.ql.io.parquet.MapredParquetInputFormat' \n" +
            "OUTPUTFORMAT 'org.apache.hadoop.hive.ql.io.parquet.MapredParquetOutputFormat'\n" +
            ";";

    public static final String createClientParquetSchedulerTemplateNullColumns = "insert overwrite table ${hive_database_name}.applog_${eid} partition (ymd='${last_1_day_int}')\n" +
            "select case when cast(str['md_userid'] as bigint)>0 and cast(str['md_userid'] as bigint)<4300000000 then cast(str['md_userid'] as bigint) else 0 end as uid,\n" +
            "cast(str['md_mod'] as int) as md_mod,\n" +
            "str['md_session'] as md_session,\n" +
            "cast(str['md_etime'] as bigint) as client_ts,\n" +
            "from_unixtime(cast(substr(str['md_etime'],1,10) as bigint)) as client_time,\n" +
            "cast(str['record_time'] as bigint) as record_ts,\n" +
            "from_unixtime(cast(substr(str['record_time'],1,10) as bigint)) as record_time,\n" +
            "       str['lc'] as lc,\n" +
            "       str['cv'] as cv,\n" +
            "       str['cc'] as cc,\n" +
            "       str['ua'] as ua,\n" +
            "       str['devi'] as devi,\n" +
            "       str['imsi'] as imsi,\n" +
            "       str['imei'] as imei,\n" +
            "       str['oaid'] as oaid,\n" +
            "       inke_smid_udf(str['smid']) as smid,\n" +
            "       str['idfa'] as idfa,\n" +
            "       str['idfv'] as idfv,\n" +
            "       str['ndid'] as ndid,\n" +
            "       str['aid'] as aid,\n" +
            "       str['conn'] as conn,\n" +
            "       str['osversion'] as osversion,\n" +
            "       str['client_ip'] as ip,\n" +
            "       case when str['md_userid']=str['uid'] then nvl(str['md_logid'], str['logid']) else str['md_logid'] end as logid,\n" +
            "       nvl(cast(str['jb'] as int),0) as ropklv,\n" +
            "       str['latitude'] as latitude,\n" +
            "       str['longitude'] as longitude,\n" +
            "       str['md_path'] as path,\n" +
            "       str['cpu'] as cpu \n" +
            "from (select str from ${appname}logs.${appname}_serverlog where eid='${eid}' and ymd='${last_1_day_int}' group by str) t\n" +
            ";";
}
