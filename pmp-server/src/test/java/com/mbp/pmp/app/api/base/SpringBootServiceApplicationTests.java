package com.mbp.pmp.app.api.base;

import com.mbp.eng.framework.common.util.date.DateUtil;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.PreDestroy;
import java.util.concurrent.atomic.AtomicBoolean;

@Ignore
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = Application.class)
public class SpringBootServiceApplicationTests {

    private String className = this.getClass().getName();
    private String simpleClassName = this.getClass().getSimpleName();
    private static final Logger logger = LoggerFactory.getLogger(SpringBootServiceApplicationTests.class);

    protected AtomicBoolean atomicBoolean = new AtomicBoolean(false);

    public void setEnableProxy(boolean enableProxy) {
        long time = System.currentTimeMillis();
        logger.info("==========SpringBootServiceApplicationTests_setEnableProxy_time:{}", DateUtil.getFormatTime(time));
        this.atomicBoolean.set(enableProxy);
        if (this.atomicBoolean.get()) {
            setProxy();
        }
    }

    @Before
    public void init() {
        long time = System.currentTimeMillis();
        logger.info("==========SpringBootServiceApplicationTests_init_time:{}", DateUtil.getFormatTime(time));
        if (atomicBoolean.get()) {
            setProxy();
        } else {
            destroy();
        }
    }

    /**
     * 设置代理,用于本地测试
     */
    public void setProxy() {
        long time = System.currentTimeMillis();
        logger.info("==========SpringBootServiceApplicationTests_setProxy_time:{}", DateUtil.getFormatTime(time));
        System.setProperty("socksProxyVersion", "5");
        System.setProperty("socksProxyHost", "172.22.178.100");
        System.setProperty("socksProxyPort", "80");
    }

    /**
     * 清除代理,用于本地测试
     */
    @PreDestroy
    public void destroy() {
        long time = System.currentTimeMillis();
        logger.info("==========SpringBootServiceApplicationTests_destroy_time:{}", DateUtil.getFormatTime(time));
        System.clearProperty("socksProxyVersion");
        System.clearProperty("socksProxyHost");
        System.clearProperty("socksProxyPort");
    }

    @Test
    public void contextLoads() {
        System.out.println("======>>> test");
    }
}
