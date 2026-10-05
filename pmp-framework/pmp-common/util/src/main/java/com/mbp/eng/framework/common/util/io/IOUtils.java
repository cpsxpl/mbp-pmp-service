package com.mbp.eng.framework.common.util.io;

import cn.hutool.core.io.IORuntimeException;
import cn.hutool.core.io.IoUtil;
import cn.hutool.core.util.StrUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class IOUtils {
    protected static final Logger logger = LoggerFactory.getLogger(IOUtils.class);
    private static final String LINE_SEPARATOR = System.getProperty("line.separator");

    public static String stream2String(InputStream inputStream) throws IOException {
        if (inputStream == null) {
            return null;
        }
        BufferedReader bufferedReader = null;
        try {
            bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
            StringBuilder sb = new StringBuilder();
            String line = null;
            while ((line = bufferedReader.readLine()) != null) {
                if (sb.length() > 0) {
                    sb.append(LINE_SEPARATOR);
                }
                sb.append(line);
            }
            return sb.toString();
        } catch (Exception e) {
            logger.info("stream2String failed ", e);
        } finally {
            if (bufferedReader != null) {
                bufferedReader.close();
            }
        }
        return "";
    }

    /**
     * 从流中读取 UTF8 编码的内容
     *
     * @param in      输入流
     * @param isClose 是否关闭
     * @return 内容
     * @throws IORuntimeException IO 异常
     */
    public static String readUtf8(InputStream in, boolean isClose) throws IORuntimeException {
        return StrUtil.utf8Str(IoUtil.read(in, isClose));
    }
}
