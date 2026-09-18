package com.test.spring.example.v1;

/**
 * 注解具体实现类:专门负责处理配置类和包扫描
 */
public class AnnotationConfigApplicationContext extends AbstractApplicationContext {
    private Class<?> configClass;

    /**
     * 构造方法:传入配置类的 Class 对象
     */
    public AnnotationConfigApplicationContext(Class<?> configClass) {
        this.configClass = configClass;
        // 关键点:在构造时,必须触发父类的 refresh() 模板流程
        this.refresh();
    }

    /**
     * 实现父类的抽象方法:执行专属于注解驱动的特有逻辑
     */
    @Override
    protected void loadBeanDefinitions() {
        System.out.println("[注解分支驱动]-> 检测到配置类: " + configClass.getSimpleName());
        System.out.println("[注解分支驱动]-> 正在启动 ClassPathBeanDefinitionScanner 扫描包路径...");
        System.out.println("[注解分支驱动]-> 成功解析 @Service, @Component 并注册元数据");
    }
}

