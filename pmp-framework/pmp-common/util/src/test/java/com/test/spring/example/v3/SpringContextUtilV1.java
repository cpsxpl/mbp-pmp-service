package com.test.spring.example.v3;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.util.Assert;

public class SpringContextUtilV1 {
    private static final Logger logger = LoggerFactory.getLogger(SpringContextUtilV1.class);

    // 1. 必须加 volatile 关键字,防止双重检查锁中的指令重排序
    private static volatile AbstractApplicationContext abstractApplicationContext;

    private SpringContextUtilV1() {
        // 防止通过反射破坏单例构造
    }

    /**
     * 取得ApplicationContext(双重检查锁保证线程安全懒加载）
     */
    public static ApplicationContext getApplicationContext(String... configLocations) {
        if (abstractApplicationContext == null) {
            synchronized (SpringContextUtilV1.class) {
                if (abstractApplicationContext == null) {
                    logger.info("Initializing spring context with locations: {}", (Object) configLocations);
                    Assert.notEmpty(configLocations, "初始化 Spring 容器时,配置文件路径不能为空");
                    // 实例化具体子类
                    abstractApplicationContext = new ClassPathXmlApplicationContext(configLocations);
                    // 注册 JVM 关闭钩子,确保程序退出时优雅释放资源
                    abstractApplicationContext.registerShutdownHook();
                    logger.info("Complete spring context initialization.");
                }
            }
        } else if (configLocations != null && configLocations.length > 0) {
            // 提示开发者:后续传入的配置文件不会生效
            logger.warn("Spring context has already been initialized. Incoming configLocations ignored.");
        }
        return abstractApplicationContext;
    }

    /**
     * 从ApplicationContext中取得Bean, 自动转换类型.
     */
    @SuppressWarnings("unchecked")
    public static <T> T getBean(String name) {
        Assert.notNull(abstractApplicationContext, "ApplicationContext没有初始化,请先调用 getApplicationContext(String... configLocations)");
        return (T) abstractApplicationContext.getBean(name);
    }

    /**
     * 从ApplicationContext中取得Bean, 自动转换类型.
     * [已修正]:直接调用 getBean(Class),而不是 getBeansOfType
     */
    public static <T> T getBean(Class<T> clazz) {
        Assert.notNull(abstractApplicationContext, "ApplicationContext没有初始化,请先调用 getApplicationContext(String... configLocations)");
        return abstractApplicationContext.getBean(clazz);
    }

    /**
     * 释放资源
     */
    public static void releaseContext() {
        synchronized (SpringContextUtilV1.class) {
            if (abstractApplicationContext != null) {
                logger.info("Closing spring context...");
                abstractApplicationContext.close();
                abstractApplicationContext = null;
                logger.info("Spring context released.");
            }
        }
    }
}
