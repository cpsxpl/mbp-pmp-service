package com.mbp.eng.framework.common.util.sql;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mbp.eng.framework.common.enums.ClientTypeEnum;
import com.mbp.eng.framework.common.util.date.DateUtil;
import com.mbp.eng.framework.common.util.obj.CheckObjectUtils;
import com.mbp.eng.framework.common.util.str.StrUtil;
import org.apache.commons.lang3.tuple.Pair;
import org.quartz.CronExpression;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SQLTests {
    private String className = this.getClass().getName();
    private String simpleClassName = this.getClass().getSimpleName();
    private static Logger logger = LoggerFactory.getLogger(SQLTests.class);
    private ObjectMapper objectMapper = new ObjectMapper();

    public void testCreateSQL() {
        long time = System.currentTimeMillis();
        String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
        logger.info("{}.{} time: is {}", simpleClassName, methodName, DateUtil.getFormatTime(time));

        String appKey = "cpsxpl";
        if (ClientTypeEnum.getIfPresent(appKey) == null) {
            System.out.println("=========");
        }

        String appname = "test";
        String eid = "123";
        String dbname = "hive";
        String tableName = "test";
        String eidName = "tt_test";

        Pair<String, String> pair = DataKeyTemplate.createClientChannel();
        String template = pair.getLeft();
        HashMap<String, Object> params = new HashMap<>();
        params.put("appname", appname);
        //params.put("get_json_object_columns", getJsonObjectColumns);

        //params.put("columns", columns);
        //params.put("hiveTableName", hiveTableName);
        params.put("database", dbname);
        params.put("tablename", tableName);
        //params.put("get_json_object_parquet", get_json_object_parquet);
        params.put("hive_database_name", dbname);
        params.put("eid", eid);
        params.put("eid_name", eidName);
        params.put("hive_db_cname", eidName);
        StrUtil.resolvedResult(template, params);
    }

    public void testListNull() {
        long time = System.currentTimeMillis();
        String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
        logger.info("{}.{} time: is {}", simpleClassName, methodName, DateUtil.getFormatTime(time));
        /*int type = 1;
        System.out.println(type == 0 ? "serverlog" : type == 1 ? "applog" : null);*/
        List<Map<String, Object>> list = new ArrayList<>();
        Map<String, Object> map = new HashMap<>();
        map.put("scriptId", " 123");
        map.put("taskId", " 456");
        list.add(map);
        System.out.println("=====" + list.size());
        Object scriptId = list.get(0).get("scriptId");
        Object taskId = list.get(0).get("taskId");
        System.out.println("=====" + scriptId);
        System.out.println("=====" + taskId);
        System.out.println("=====" + String.valueOf(scriptId).matches("[0-9]{1,}"));

        if (list != null && list.size() == 1 && CheckObjectUtils.isNotNullNum(scriptId) && CheckObjectUtils.isNotNullNum(taskId)) {
            System.out.println("=====" + Long.valueOf(String.valueOf(scriptId)));
            System.out.println("=====" + Long.valueOf(String.valueOf(taskId)));
        }
    }

    public void testJsonToObj() throws JsonProcessingException {
        long time = System.currentTimeMillis();
        String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
        logger.info("{}.{} time: is {}", simpleClassName, methodName, DateUtil.getFormatTime(time));
        ObjectMapper objectMapper1 = new ObjectMapper();
        String content = "{\"id\":733,\"data_key\":\"lingxi.conlog.free_accost_auth\",\"eid\":\"free_accost_auth\",\"eid_name\":\"授权自动搭讪\",\"creator\":\"admin\",\"app_type\":0,\"app_key\":\"lingxi\",\"table_type\":0,\"addr\":\"\",\"database\":\"\",\"tablename\":\"\",\"ddl_type\":3,\"columns\":[{\"id\":126686,\"name\":\"uid\",\"type\":\"int\",\"note\":\"用户uid\",\"ddl_type\":0,\"order\":1,\"pid\":0},{\"id\":126687,\"name\":\"apm\",\"type\":\"string\",\"note\":\"包名\",\"ddl_type\":0,\"order\":2,\"pid\":0},{\"id\":126688,\"name\":\"op_time\",\"type\":\"long\",\"note\":\"操作时间\",\"ddl_type\":0,\"order\":3,\"pid\":0},{\"id\":126689,\"name\":\"status\",\"type\":\"int\",\"note\":\"授权结果\",\"ddl_type\":0,\"order\":4,\"pid\":0}],\"create_time\":\"2022-07-11 15:00:00\",\"update_time\":\"2022-07-11 15:00:00\"}";
        DataKey dataKey = objectMapper1.readerFor(DataKey.class).readValue(content);
    }

    public void other1() {
        long time = System.currentTimeMillis();
        String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
        logger.info("{}.{} time: is {}", simpleClassName, methodName, DateUtil.getFormatTime(time));
        double nowPrice = 14.87;
        double gain = 0.1;
        double gainPrice = 0.0;
        int num = 0;
        double wPrice = nowPrice * 2;

        while (nowPrice < wPrice) {
            DecimalFormat decimalFormat = new DecimalFormat("#.00");
            gainPrice = Double.parseDouble(decimalFormat.format(nowPrice + nowPrice * gain));
            num = num + 1;
            logger.info("第:{}个10%,现价:{}", num, gainPrice);
            nowPrice = gainPrice;
        }
    }

    public void other2() {
        long time = System.currentTimeMillis();
        String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
        logger.info("{}.{} time: is {}", simpleClassName, methodName, DateUtil.getFormatTime(time));
        try {
            List<Map<String, Object>> list = new ArrayList<>();
            /*Map<String, Object> map = new HashMap<>();
            map.put("scriptId", " 123");
            map.put("taskId", " 456");
            list.add(map);*/
            String scriptId = String.valueOf(list.get(0).get("ddd"));
            System.out.println(scriptId);
        } catch (Exception e) {
            logger.error("error.:" + e.getMessage(), e);
            e.printStackTrace();
        }
    }

    public void testCronExpression() {
        long time = System.currentTimeMillis();
        String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
        logger.info("{}.{} time: is {}", simpleClassName, methodName, DateUtil.getFormatTime(time));

        if (!CronExpression.isValidExpression("40 12,16 1/1 * ?")) {
            System.out.println("表达式错误");
        }
    }
}