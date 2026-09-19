package com.mbp.pmp.app.api.base;

import com.mbp.eng.framework.common.util.date.DateUtil;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup;

@Ignore
@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class)
public class SpringBootApplicationTests {

    private String className = this.getClass().getName();
    private String simpleClassName = this.getClass().getSimpleName();
    private static final Logger logger = LoggerFactory.getLogger(SpringBootApplicationTests.class);

    protected MockMvc mockMvc;

    @Autowired
    protected WebApplicationContext webApplicationContext;

    @Before
    public void setup() {
        long time = System.currentTimeMillis();
        logger.info("==========SpringBootApplicationTests_setup_time:{}==========", DateUtil.getFormatTime(time));
        this.mockMvc = webAppContextSetup(this.webApplicationContext).build();
    }

}
