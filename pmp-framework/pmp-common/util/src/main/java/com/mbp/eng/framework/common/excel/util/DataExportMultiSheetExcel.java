package com.mbp.eng.framework.common.excel.util;

import cn.afterturn.easypoi.excel.entity.ExportParams;
import cn.afterturn.easypoi.excel.entity.enmus.ExcelType;
import cn.afterturn.easypoi.excel.export.ExcelExportService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mbp.eng.framework.common.excel.bean.ExportView;
import com.mbp.eng.framework.common.excel.style.CustomizeExcelExportStylerBorder;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.net.URLEncoder;
import java.util.List;

/**
 * 多sheet导出
 */
public class DataExportMultiSheetExcel {
    private static final Logger logger = LoggerFactory.getLogger(DataExportMultiSheetExcel.class);

    private static final String FILE_SUFFIX = ".xlsx";
    private static ObjectMapper objectMapper = new ObjectMapper();

    private static void exportExcel(List<ExportView> exportViewList,
                                    HttpServletResponse httpServletResponse,
                                    String fileName) {
        try {
            //目前默认设置为写出 xlsx 类型
            ExcelType excelType = ExcelType.XSSF;
            Workbook workbook = new XSSFWorkbook();
            ExcelExportService excelExportService = new ExcelExportService();

            for (ExportView exportView : exportViewList) {
                //设置格式
                ExportParams exportParams = exportView.getExportParams();
                exportParams.setType(excelType);
                exportParams.setStyle(CustomizeExcelExportStylerBorder.class);
                logger.info(exportView.getDataList().toString());
                excelExportService.createSheet(workbook, exportParams, exportView.getCls(), exportView.getDataList());
            }
            httpServletResponse.setCharacterEncoding("UTF-8");
            httpServletResponse.setHeader("content-Type", "application/vnd.ms-excel");
            httpServletResponse.setHeader("Content-Disposition", "attachment;filename=" + URLEncoder.encode(fileName + FILE_SUFFIX, "UTF-8"));
            ServletOutputStream outputStream = httpServletResponse.getOutputStream();
            //写出
            workbook.write(outputStream);
            outputStream.flush();
            outputStream.close();
        } catch (IOException e) {
            logger.error("export to excel error:" + e.getMessage(), e);
        }
    }
}
