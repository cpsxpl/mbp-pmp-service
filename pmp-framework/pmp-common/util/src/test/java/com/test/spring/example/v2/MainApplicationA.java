package com.test.spring.example.v2;

import com.test.spring.config.AppConfig;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

/**
 * 情况 A:基于注解/Java 配置(推荐）
 */
public class MainApplicationA {
    public static void main(String[] args) {
        // 1. 初始化容器,AppConfig.class 是你的 Spring 配置主类
        AnnotationConfigApplicationContext annotationConfigApplicationContext = new AnnotationConfigApplicationContext(AppConfig.class);

        // 2. 将容器实例注入到工具类中
        com.test.spring.example.v2.SpringContextUtilV2.setApplicationContext(annotationConfigApplicationContext);

        // 3. 验证:此时可以在任何地方通过工具类获取 Bean
        com.test.spring.example.v2.MyService myService = com.test.spring.example.v2.SpringContextUtilV2.getBean(com.test.spring.example.v2.MyService.class);
        myService.execute();

        // 注册钩子,确保 JVM 退出时优雅关闭 Spring 容器
        annotationConfigApplicationContext.registerShutdownHook();
    }
}