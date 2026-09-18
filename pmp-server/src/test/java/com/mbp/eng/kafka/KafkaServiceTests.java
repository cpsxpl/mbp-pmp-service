package com.mbp.eng.kafka;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mbp.eng.framework.common.util.date.DateUtil;
import com.mbp.eng.framework.common.util.json.JsonUtil;
import com.mbp.eng.kafka.send.KafkaSender;
import com.mbp.eng.kafka.send.KafkaSenderDemo;
import com.mbp.eng.module.example.domain.schedulerTaskInfo.SchedulerTaskInfo;
import com.mbp.pmp.app.api.base.SpringBootServiceApplicationTests;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.Resource;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

//@Ignore
public class KafkaServiceTests extends SpringBootServiceApplicationTests {

    private String className = this.getClass().getName();
    private String simpleClassName = this.getClass().getSimpleName();
    private static Logger logger = LoggerFactory.getLogger(KafkaServiceTests.class);

    private ObjectMapper objectMapper = new ObjectMapper();


    @Autowired
    private KafkaSender kafkaSender;

    @Resource
    private KafkaSenderDemo kafkaSenderDemo;

    @Test
    public void sendMsg() {
        long time = System.currentTimeMillis();
        logger.info("time: is {}", DateUtil.getFormatTime(time));
        List<SchedulerTaskInfo> demoList = new ArrayList<>();
        SchedulerTaskInfo schedulerTaskInfo = new SchedulerTaskInfo();
        schedulerTaskInfo.setId(1);
        schedulerTaskInfo.setStatus(1);
        schedulerTaskInfo.setCreatedBy("test");
        schedulerTaskInfo.setCreateTime(new Timestamp(time));
        demoList.add(schedulerTaskInfo);
        kafkaSender.sendMsg(JsonUtil.toJSON(demoList));
    }

    @Test
    public void sendMsgDemo() {
        long time = System.currentTimeMillis();
        logger.info("time: is {}", DateUtil.getFormatTime(time));
        List<SchedulerTaskInfo> schedulerTaskInfoList = new ArrayList<>();
        SchedulerTaskInfo schedulerTaskInfo = new SchedulerTaskInfo();
        schedulerTaskInfo.setId(2);
        schedulerTaskInfo.setStatus(2);
        schedulerTaskInfo.setCreatedBy("test_demo");
        schedulerTaskInfo.setCreateTime(new Timestamp(time));
        schedulerTaskInfoList.add(schedulerTaskInfo);
        kafkaSenderDemo.sendMsgDemo(JsonUtil.toJSON(schedulerTaskInfo));
    }
}
