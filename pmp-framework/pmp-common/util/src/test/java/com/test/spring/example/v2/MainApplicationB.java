package com.test.spring.example.v2;

import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * 情况 B:基于 XML 配置文件
 */
public class MainApplicationB {
    public static void main(String[] args) {
        // 1. 从类路径(resources）下加载 XML 配置文件初始化容器
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("applicationContext.xml");

        // 2. 将容器实例注入到工具类中
        com.test.spring.example.v2.SpringContextUtilV2.setApplicationContext(context);

        // 3. 测试获取 Bean
        com.test.spring.example.v2.MyService myService = com.test.spring.example.v2.SpringContextUtilV2.getBean("myService");
        myService.execute();

        // 注册钩子优雅关闭
        context.registerShutdownHook();
    }
}
