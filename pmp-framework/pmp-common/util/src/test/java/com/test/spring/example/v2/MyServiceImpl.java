package com.test.spring.example.v2;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

// 关键点:使用 @Service 注解.由于 AppConfig 配置了 @ComponentScan("com.example"),
// Spring 容器启动时会自动发现并实例化这个类.
@Service
public class MyServiceImpl implements com.test.spring.example.v2.MyService {
    private static final Logger logger = LoggerFactory.getLogger(MyServiceImpl.class);

    @Override
    public void execute() {
        logger.info("-> MyService 正在核心业务逻辑...");
        // 你可以在这里编写具体的业务,比如查询数据库、处理文件等
        logger.info("-> 业务逻辑处理完成.");
    }
}