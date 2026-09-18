package com.test.spring.example.v1;

import java.util.HashMap;
import java.util.Map;

/**
 * 抽象父类:掌管整个生命周期的核心骨架
 */
public abstract class AbstractApplicationContext {
    // 模拟 Spring 底层的单例池(一级缓存）,存放最终创建好的 Bean 实例
    protected Map<String, Object> singletonObjects = new HashMap<>();

    /**
     * 核心模板方法:规定了所有容器启动时必须走的标准流程
     */
    public final void refresh() {
        System.out.println("\n[" + this.getClass().getSimpleName() + "]=== 开始启动 Spring 核心骨架流程 ===");

        // 步骤 1:调用抽象方法,强制交由子类去实现各自的配置解析
        loadBeanDefinitions();

        // 步骤 2:公共逻辑,统一由父类处理 Bean 的创建
        System.out.println("[核心骨架]统一将 BeanDefinition 实例化为真正的单例对象...");
        createAndCacheBeans();

        System.out.println("[核心骨架]=== Spring 容器初始化成功 ===\n");
    }

    /**
     * 抽象方法:具体怎么去读配置,父类不管,全权交给子类去实现
     */
    protected abstract void loadBeanDefinitions();

    /**
     * 父类的通用公共方法:模拟 Bean 的创建和依赖注入
     */
    private void createAndCacheBeans() {
        // 真实 Spring 会通过反射技术(如 clazz.newInstance()）创建
        singletonObjects.put("userService", "我是通过容器创建的 UserService 业务对象");
    }

    /**
     * 父类的通用公共方法:对外部提供获取 Bean 的统一接口
     */
    public Object getBean(String name) {
        return singletonObjects.get(name);
    }
}
