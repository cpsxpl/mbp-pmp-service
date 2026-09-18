package com.mbp.eng.framework.common.excel.test;

import cn.afterturn.easypoi.excel.ExcelExportUtil;
import cn.afterturn.easypoi.excel.entity.ExportParams;
import cn.afterturn.easypoi.excel.entity.enmus.ExcelType;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mbp.eng.framework.common.excel.style.CustomizeExcelExportStylerBorder;
import org.apache.poi.ss.usermodel.Workbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class DownloadTestUtil {
    private String className = this.getClass().getName();
    private String simpleClassName = this.getClass().getSimpleName();
    private static final Logger logger = LoggerFactory.getLogger(DownloadTestUtil.class);

    private static ObjectMapper objectMapper = new ObjectMapper();

    public static <T> void exportExcel(List<Map<String, Object>> data,
                                       Class<T> clz,
                                       String fileName,
                                       String title,
                                       String sheetName) {
        try {
            //数据 map export model
            //List<T> dataList = objectMapper.readValue(objectMapper.writeValueAsString(data),
            //        new TypeReference<List<T>>(){});
            List<T> dataList = new ArrayList<>();
            for (Map<String, Object> map : data) {
                T dataModel = objectMapper.readValue(objectMapper.writeValueAsString(map),
                        clz);
                dataList.add(dataModel);
            }

            //输出日志
            logger.info(dataList.toString());

            //目前默认设置为写出 xlsx 类型
            ExcelType excelType = ExcelType.XSSF;

            ExportParams params = new ExportParams(title, sheetName, excelType);

            //设置表格的基础风格
            params.setStyle(CustomizeExcelExportStylerBorder.class);
            params.setTitleHeight((short) 8);

            //生成excel
            Workbook workbook = ExcelExportUtil.exportExcel(params, clz, dataList);

            FileOutputStream outputStream = new FileOutputStream(fileName);
            //写出
            workbook.write(outputStream);
            outputStream.flush();
            outputStream.close();
        } catch (IOException e) {
            logger.error("export to excel error:" + e.getMessage(), e);
        }
    }
}
