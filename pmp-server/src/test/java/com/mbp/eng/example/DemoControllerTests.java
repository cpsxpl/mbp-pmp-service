package com.mbp.eng.example;

import com.mbp.pmp.app.api.base.SpringBootApplicationTests;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

//@Ignore
//@PropertySource("classpath:application.yml")
public class DemoControllerTests extends SpringBootApplicationTests {

    private String className = this.getClass().getName();
    private String simpleClassName = this.getClass().getSimpleName();
    private static Logger logger = LoggerFactory.getLogger(DemoControllerTests.class);

    @Test
    public void test() throws Exception {
        //POST请求
        /*final MvcResult result = mockMvc.perform(post("/mbp/api/demo/xxx")
                .param("param1", "aaa"))
                .param("param2", "bbb"))
                .andExpect(status().isOk())
                .andReturn();*/

        //GET请求
        System.out.println("demoQuery==========" + mockMvc.perform(
                get("/mbp/api/demo/demoQuery")
                        .param("status", String.valueOf(1))
                        .param("createdBy", "test0"))
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString());

        /*System.out.println("demoExport==========" + mockMvc.perform(
                get("/mbp/api/demo/demoExport")
                        .param("status", String.valueOf(1)))
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString());*/
    }
}
