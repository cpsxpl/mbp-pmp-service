package com.test.spring.example.v3;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mbp.eng.framework.common.util.spring.SpringContextUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MainApplicationTest {
    private static Logger logger = LoggerFactory.getLogger(MainApplicationTest.class);
    private static ObjectMapper objectMapper = new ObjectMapper();

    public static void main(String[] args) throws Exception {
        // 初始化SpringContext
        SpringContextUtil.getApplicationContext("classpath:spring/applicationContext.xml");

        // 初始化DataSource
        //BasicDataSource basicDataSource = SpringContextUtil.getBean("dataSource");
    }
}