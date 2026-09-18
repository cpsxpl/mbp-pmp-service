package com.mbp.eng.framework.common.excel.util;

import cn.afterturn.easypoi.excel.ExcelExportUtil;
import cn.afterturn.easypoi.excel.entity.ExportParams;
import cn.afterturn.easypoi.excel.entity.enmus.ExcelType;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mbp.eng.framework.common.excel.style.CustomizeExcelExportStylerBorder;
import org.apache.poi.ss.usermodel.Workbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 单sheet导出
 */
public class DataExportExcel {
    private static final Logger logger = LoggerFactory.getLogger(DataExportExcel.class);

    private static final String FILE_SUFFIX = ".xlsx";
    private static ObjectMapper objectMapper = new ObjectMapper();

    public static <T> void exportExcel(List<Map<String, Object>> data,
                                       Class<T> tClass,
                                       HttpServletResponse httpServletResponse,
                                       String fileName,
                                       String title,
                                       String sheetName,
                                       int titleHeight) {
        try {
            //数据 map export model
            //List<T> dataList = objectMapper.readValue(objectMapper.writeValueAsString(data),
            //        new TypeReference<List<T>>(){});
            List<T> list = new ArrayList<>();
            if (data != null && data.size() > 0) {
                for (Map<String, Object> map : data) {
                    T dataModel = objectMapper.readValue(objectMapper.writeValueAsString(map), tClass);
                    list.add(dataModel);
                }
            }

            //输出日志
            logger.info("==========DataExportExcel_list:{}", list.toString());

            //目前默认设置为写出 xlsx 类型
            ExcelType excelType = ExcelType.XSSF;

            ExportParams exportParams = new ExportParams(title, sheetName, excelType);

            //设置表格的基础风格
            exportParams.setStyle(CustomizeExcelExportStylerBorder.class);
            if (titleHeight == 0) {
                titleHeight = 8;
            }
            exportParams.setTitleHeight((short) titleHeight);

            //生成excel
            Workbook workbook = ExcelExportUtil.exportExcel(exportParams, tClass, list);

            httpServletResponse.setCharacterEncoding("UTF-8");
            httpServletResponse.setHeader("content-Type", "application/vnd.ms-excel");
            // Excel下载后的文件名必须encode
            httpServletResponse.setHeader("Content-Disposition", "attachment;filename=" + URLEncoder.encode(fileName + FILE_SUFFIX, "UTF-8"));
            ServletOutputStream servletOutputStream = httpServletResponse.getOutputStream();
            //写出
            workbook.write(servletOutputStream);
            servletOutputStream.flush();
            servletOutputStream.close();
        } catch (IOException e) {
            logger.error("export to excel error:" + e.getMessage(), e);
        }
    }
}
