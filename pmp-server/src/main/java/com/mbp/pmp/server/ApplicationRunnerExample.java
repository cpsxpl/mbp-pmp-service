package com.mbp.pmp.server;

import com.mbp.eng.framework.common.util.date.DateUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@SpringBootApplication//标注该注解才能扫描程序里面的注解组件
@Component//被spring容器管理
@Order(1)//如果多个自定义ApplicationRunner,用来标明执行顺序
public class ApplicationRunnerExample implements ApplicationRunner {
    private static Logger logger = LoggerFactory.getLogger(ApplicationRunnerExample.class);

    @Override
    public void run(ApplicationArguments applicationArguments) {
        long time = System.currentTimeMillis();
        logger.info(String.format("ApplicationRunnerExample.run time=%s ", DateUtil.getFormatTime(time)));
    }
}
