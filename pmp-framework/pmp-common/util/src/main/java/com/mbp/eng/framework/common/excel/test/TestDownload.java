package com.mbp.eng.framework.common.excel.test;

import com.fasterxml.jackson.core.type.TypeReference;
import com.mbp.eng.framework.common.excel.model.LifecycleLayoutTrendModel;
import com.mbp.eng.framework.common.util.json.JsonUtil;

import java.io.File;
import java.util.List;
import java.util.Map;

public class TestDownload {
    public void testDownLoadLayoutData() {
        String dataJson = "[\n" +
                "    {\n" +
                "        \"processDate\":\"2020-01-10\",\n" +
                "        \"aware\":11,\n" +
                "        \"appeal\":22,\n" +
                "        \"firstPurchase\":33,\n" +
                "        \"growth\":44,\n" +
                "        \"maturity\":55,\n" +
                "        \"decline\":66,\n" +
                "        \"loss\":77\n" +
                "    },\n" +
                "    {\n" +
                "        \"processDate\":\"2020-01-11\",\n" +
                "        \"aware\":111,\n" +
                "        \"appeal\":222,\n" +
                "        \"firstPurchase\":333,\n" +
                "        \"growth\":444,\n" +
                "        \"maturity\":555,\n" +
                "        \"decline\":666,\n" +
                "        \"loss\":777\n" +
                "    }\n" +
                "]";
        List<Map<String, Object>> data = JsonUtil.deserialize(dataJson, new TypeReference<List<Map<String, Object>>>() {
        });
        String startDate = "2020-01-10";
        String endDate = "2020-01-11";
        //设置文件名、title、sheet名等信息
        String lifeCycleName = "Layout";
        String filename = "/tmp/" + lifeCycleName + "_" + startDate + "至" + endDate + ".xlsx";
        String title = lifeCycleName + "；时间段=" + startDate + "至" + endDate;
        String sheetName = null;
        try {
            //写出excel
            DownloadTestUtil.exportExcel(data, LifecycleLayoutTrendModel.class, filename, title, sheetName);
            File resultFile = new File(filename);
            resultFile.deleteOnExit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
