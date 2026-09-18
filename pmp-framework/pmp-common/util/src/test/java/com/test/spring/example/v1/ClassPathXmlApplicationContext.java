package com.test.spring.example.v1;

/**
 * XML 具体实现类:专门负责处理类路径下的 XML 配置文件
 */
public class ClassPathXmlApplicationContext extends AbstractApplicationContext {
    private String xmlPath;

    /**
     * 构造方法:传入 XML 文件的相对路径
     */
    public ClassPathXmlApplicationContext(String xmlPath) {
        this.xmlPath = xmlPath;
        // 关键点:同样在构造时,必须触发父类的 refresh() 模板流程
        this.refresh();
    }

    /**
     * 实现父类的抽象方法:执行专属于 XML 驱动的特有逻辑
     */
    @Override
    protected void loadBeanDefinitions() {
        System.out.println("[XML 分支驱动]-> 检测到配置文件路径: " + xmlPath);
        System.out.println("[XML 分支驱动]-> 正在启动 DOM4J/SAX 解析器读取 xml 文件流...");
        System.out.println("[XML 分支驱动]-> 成功解析 <bean id=\"...\" class=\"...\"> 标签并注册元数据");
    }
}

