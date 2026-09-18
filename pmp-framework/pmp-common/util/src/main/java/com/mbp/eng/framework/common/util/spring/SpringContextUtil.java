package com.mbp.eng.framework.common.util.spring;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.util.Assert;

public class SpringContextUtil {
    private static Logger logger = LoggerFactory.getLogger(SpringContextUtil.class);

    private static AbstractApplicationContext abstractApplicationContext;

    private SpringContextUtil() {
    }

    /**
     * 取得ApplicationContext.
     */
    public static ApplicationContext getApplicationContext(String... configLocations) {
        logger.info("Initializing spring context...");
        if (abstractApplicationContext == null) {
            synchronized (SpringContextUtil.class) {
                if (abstractApplicationContext == null) {
                    abstractApplicationContext = new ClassPathXmlApplicationContext(configLocations);
                }
            }
        }
        logger.info("Complete spring context...");
        return abstractApplicationContext;
    }

    /**
     * 从AplicationContext中取得Bean,自动转换类型.
     */
    public static <T> T getBean(String name) {
        Assert.notNull(abstractApplicationContext, "ApplicationContext没有初始化");
        return (T) abstractApplicationContext.getBean(name);
    }

    /**
     * 从AplicationContext中取得Bean,自动转换类型.
     */
    public static <T> T getBean(Class<T> clazz) {
        Assert.notNull(abstractApplicationContext, "ApplicationContext没有初始化");
        return (T) abstractApplicationContext.getBeansOfType(clazz);
    }

    /**
     * 释放资源
     */
    public static void releaseContext() {
        if (abstractApplicationContext != null) {
            abstractApplicationContext.close();
            abstractApplicationContext = null;
        }
    }
}
