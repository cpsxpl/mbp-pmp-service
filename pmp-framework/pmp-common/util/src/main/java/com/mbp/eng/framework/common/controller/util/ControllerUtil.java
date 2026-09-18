package com.mbp.eng.framework.common.controller.util;

import com.mbp.eng.framework.common.util.date.DateUtil;
import com.mbp.eng.framework.common.util.json.JsonUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.IOException;

public class ControllerUtil {
    private static final Logger logger = LoggerFactory.getLogger(ControllerUtil.class);

    public static void redirect(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object object, String path) throws IOException {
        long time = System.currentTimeMillis();
        logger.info("==========ControllerUtil_redirect_time:{}", DateUtil.getFormatTime(time));
        if (isAjaxRequest(httpServletRequest)) {
            logger.info("==========ControllerUtil_object:{}", JsonUtil.toJSON(object));
            httpServletResponse.getWriter().write(JsonUtil.toJSON(object));
        } else {
            httpServletResponse.sendRedirect(path);
        }
    }

    public static boolean isAjaxRequest(HttpServletRequest httpServletRequest) {
        long time = System.currentTimeMillis();
        logger.info("==========ControllerUtil_isAjaxRequest_time:{}", DateUtil.getFormatTime(time));
        String requestType = httpServletRequest.getHeader("X-Requested-With");
        return "XMLHttpRequest".equalsIgnoreCase(requestType) || httpServletRequest.getHeader("adx-fe") != null;
    }
}
