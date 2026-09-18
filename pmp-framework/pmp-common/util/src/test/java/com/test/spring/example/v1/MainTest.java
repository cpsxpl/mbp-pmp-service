package com.test.spring.example.v1;

public class MainTest {
    // 模拟一个普通的 Java 配置类
    static class AppConfig {
    }

    public static void main(String[] args) {
        System.out.println("=================================================");
        System.out.println(" 场景一:利用 AnnotationConfigApplicationContext 启动");
        System.out.println("=================================================");

        // 多态:左边用抽象父类声明,右边用注解具体实现类
        AbstractApplicationContext context1 = new AnnotationConfigApplicationContext(AppConfig.class);
        Object service1 = context1.getBean("userService");
        System.out.println("从注解容器中获取的 Bean 结果: " + service1);

        System.out.println("\n=================================================");
        System.out.println(" 场景二:利用 ClassPathXmlApplicationContext 启动");
        System.out.println("=================================================");

        // 多态:左边用抽象父类声明,右边用 XML 具体实现类
        AbstractApplicationContext context2 = new ClassPathXmlApplicationContext("beans.xml");
        Object service2 = context2.getBean("userService");
        System.out.println("从 XML 容器中获取的 Bean 结果: " + service2);
    }
}