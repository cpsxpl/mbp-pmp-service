package com.test.spring.example.v2;

import org.springframework.context.support.ClassPathXmlApplicationContext;

import static com.test.spring.example.v2.SpringContextUtilV2.getBean;

/**
 * 情况 B:基于 XML 配置文件
 */
public class MainApplicationB {
    public static void main(String[] args) {
        // 1. 从类路径(resources)下加载 XML 配置文件初始化容器
        ClassPathXmlApplicationContext classPathXmlApplicationContext = new ClassPathXmlApplicationContext("applicationContext.xml");

        // 2.注册钩子(防止后续业务代码阻塞或异常导致钩子未注册),确保 JVM 退出时优雅关闭 Spring 容器
        classPathXmlApplicationContext.registerShutdownHook();

        // 3. 将容器实例注入到全局工具类中
        SpringContextUtilV2.setApplicationContext(classPathXmlApplicationContext);

        // 4. 验证:此时可以在任何地方通过工具类获取 Bean
        MyService myService = getBean("myService");
        // 5. 执行核心业务逻辑
        myService.execute();
    }
}
