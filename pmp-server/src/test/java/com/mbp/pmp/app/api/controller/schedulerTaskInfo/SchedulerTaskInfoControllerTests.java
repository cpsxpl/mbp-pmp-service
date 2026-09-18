package com.mbp.pmp.app.api.controller.schedulerTaskInfo;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.mbp.eng.framework.common.CommonResponse;
import com.mbp.eng.framework.common.util.date.DateUtil;
import com.mbp.pmp.app.api.base.SpringBootApplicationTests;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.ImportResource;
import org.springframework.context.annotation.PropertySource;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MvcResult;

import java.io.File;
import java.io.FileInputStream;
import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.springframework.http.MediaType.APPLICATION_JSON_UTF8;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.fileUpload;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

//@Ignore
@PropertySource(value = {"${important.properties.filepath}", "classpath:spark.properties"}, encoding = "utf-8")
@ImportResource(value = {"classpath:spring/applicationContext.xml"})
public class SchedulerTaskInfoControllerTests extends SpringBootApplicationTests {

    private String className = this.getClass().getName();
    private String simpleClassName = this.getClass().getSimpleName();
    private static Logger logger = LoggerFactory.getLogger(SchedulerTaskInfoControllerTests.class);

    private ObjectMapper objectMapper = new ObjectMapper();

    private ObjectWriter objectWriter;

    @Test
    public void test() throws Exception {
        long currentTimeMillis = System.currentTimeMillis();
        String currentTime = DateUtil.getFormatTime(currentTimeMillis);
        logger.info("==========AuditDetailControllerTests_time:{}", currentTime);

        Map<String, Object> map = new HashMap<>();
        map.put("remark", currentTime);
        map.put("status", "1");
        //必填写
        map.put("createdBy", "test");
        map.put("audienceId", "680aa5e3a6d545699f1027aa03bfa67d");
        //map.put("createTime", new Timestamp(System.currentTimeMillis()));
        objectMapper.configure(SerializationFeature.WRAP_ROOT_VALUE, false);
        objectWriter = objectMapper.writer().withDefaultPrettyPrinter();
        String requestJson = objectWriter.writeValueAsString(map);
        //SchedulerTaskInfo schedulerTaskInfo = objectMapper.convertValue(map, SchedulerTaskInfo.class);

        //schedulerTaskInfo接口控制类-增加 POST请求
        System.out.println("schedulerTaskInfo接口控制类-增加==========" + mockMvc.perform(
                post("/mbp/api/schedulerTaskInfo/save").contentType(APPLICATION_JSON_UTF8)
                        .content(requestJson))
                .andExpect(status().isOk())
                .andReturn());

        //schedulerTaskInfo接口控制类-删除 POST请求
        /*final MvcResult result = mockMvc.perform(post("/mbp/api/schedulerTaskInfo/delete")
                .param("status", String.valueOf(-1))
                .param("createdBy", "test"))
                .andExpect(status().isOk())
                .andReturn();*/

        //schedulerTaskInfo接口控制类-删除 POST请求
        System.out.println("schedulerTaskInfo接口控制类-删除==========" + mockMvc.perform(
                post("/mbp/api/schedulerTaskInfo/delete")
                        .param("id", String.valueOf(1180))
                        .param("status", String.valueOf(-1)))
                .andExpect(status().isOk())
                .andReturn());

        //schedulerTaskInfo接口控制类-根据id删除 DELETE请求
        System.out.println("schedulerTaskInfo接口控制类-根据id删除==========" + mockMvc.perform(
                delete("/mbp/api/schedulerTaskInfo/delete/3"))
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString());

        //schedulerTaskInfo接口控制类-修改
        map = new HashMap<>();
        map.put("id", "2");
        map.put("remark", currentTime);
        map.put("status", "1");
        //必填写
        map.put("createdBy", "test");
        requestJson = objectWriter.writeValueAsString(map);
        System.out.println("schedulerTaskInfo接口控制类-修改==========" + mockMvc.perform(
                post("/quantum/api/schedulerTaskInfo/update").contentType(APPLICATION_JSON_UTF8)
                        .content(requestJson))
                .andExpect(status().isOk())
                .andReturn());

        //schedulerTaskInfo接口控制类-查询 GET请求
        System.out.println("schedulerTaskInfo接口控制类-查询-query==========" + mockMvc.perform(
                get("/mbp/api/schedulerTaskInfo/query")
                        .param("status", String.valueOf(-1))
                        .param("createdBy", "test"))
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString());

        //schedulerTaskInfo接口控制类-查询 GET请求
        System.out.println("schedulerTaskInfo接口控制类-查询-show==========" + mockMvc.perform(
                get("/mbp/api/schedulerTaskInfo/show")
                        .param("id", "1"))
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString());
    }

    @Test
    public void testUpload() throws Exception {
        ClassLoader classLoader = getClass().getClassLoader();
        File file = new File(classLoader.getResource("sdkModule.csv").getFile());

        FileInputStream fileInputStream = new FileInputStream(file);

        final MockMultipartFile mockMultipartFile = new MockMultipartFile("file",
                "sdkModule.csv", "txt/csv", fileInputStream);


        final MvcResult mvcResult = mockMvc.perform(
                fileUpload("/quantum/api/sdkModule/uploadFile").file(mockMultipartFile)
                        .param("createdBy", "test"))
                .andExpect(status().isOk())
                .andReturn();

        String string = mvcResult.getResponse().getContentAsString();
        CommonResponse commonResponse = objectMapper.readValue(string, CommonResponse.class);
        assertEquals(0, commonResponse.getStatus().intValue());

        Map resMap = (Map) commonResponse.getResult();
        String urlStr = String.valueOf(resMap.get("url"));
        System.out.println("==========" + urlStr);
        /*URL url = new URL(urlStr);
        Scanner scanner = new Scanner(url.openStream());
        assert (scanner.hasNext());*/
    }

    @Test
    public void testSchedulerTaskInfoExport() throws Exception {
        System.out.println("schedulerTaskInfo接口控制类-导出-export==========" + mockMvc.perform(
                post("/mbp/api/schedulerTaskInfo/export")
                        .param("status", "1"))
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString());
    }
}
