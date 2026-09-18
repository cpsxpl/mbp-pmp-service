package com.test.spring.example.v2;

import org.springframework.context.ApplicationContext;

public class SpringContextUtilV2 {
    // 静态持有应用上下文
    private static ApplicationContext applicationContext;

    // 提供给 main 方法进行手工初始化的方法
    public static void setApplicationContext(ApplicationContext applicationContext) {
        applicationContext = applicationContext;
    }

    public static ApplicationContext getApplicationContext() {
        return applicationContext;
    }

    // 根据名称获取 Bean
    @SuppressWarnings("unchecked")
    public static <T> T getBean(String name) {
        return (T) applicationContext.getBean(name);
    }

    // 根据类型获取 Bean
    public static <T> T getBean(Class<T> clazz) {
        return applicationContext.getBean(clazz);
    }
}
