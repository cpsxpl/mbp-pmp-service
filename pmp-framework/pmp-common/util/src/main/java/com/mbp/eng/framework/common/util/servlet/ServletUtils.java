package com.mbp.eng.framework.common.util.servlet;

import cn.hutool.core.io.IoUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.extra.servlet.ServletUtil;
import com.mbp.eng.framework.common.util.json.JsonUtils;
import org.springframework.http.MediaType;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.net.URLEncoder;
import java.util.Map;

/**
 * 客户端工具类
 */
public class ServletUtils {
    /**
     * 返回 JSON 字符串
     *
     * @param httpServletResponse 响应
     * @param object   对象,会序列化成 JSON 字符串
     */
    @SuppressWarnings("deprecation") // 必须使用 APPLICATION_JSON_UTF8_VALUE,否则会乱码
    public static void writeJSON(HttpServletResponse httpServletResponse, Object object) {
        String content = JsonUtils.toJsonString(object);
        ServletUtil.write(httpServletResponse, content, MediaType.APPLICATION_JSON_UTF8_VALUE);
    }

    /**
     * 返回附件
     *
     * @param httpServletResponse 响应
     * @param filename 文件名
     * @param content  附件内容
     */
    public static void writeAttachment(HttpServletResponse httpServletResponse, String filename, byte[] content) throws IOException {
        // 设置 header 和 contentType
        httpServletResponse.setHeader("Content-Disposition", "attachment;filename=" + URLEncoder.encode(filename, "UTF-8"));
        httpServletResponse.setContentType(MediaType.APPLICATION_OCTET_STREAM_VALUE);
        // 输出附件
        IoUtil.write(httpServletResponse.getOutputStream(), false, content);
    }

    /**
     * @param httpServletRequest 请求
     * @return ua
     */
    public static String getUserAgent(HttpServletRequest httpServletRequest) {
        String ua = httpServletRequest.getHeader("User-Agent");
        return ua != null ? ua : "";
    }

    /**
     * 获得请求
     *
     * @return HttpServletRequest
     */
    public static HttpServletRequest getRequest() {
        RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();
        if (!(requestAttributes instanceof ServletRequestAttributes)) {
            return null;
        }
        return ((ServletRequestAttributes) requestAttributes).getRequest();
    }

    public static String getUserAgent() {
        HttpServletRequest httpServletRequest = getRequest();
        if (httpServletRequest == null) {
            return null;
        }
        return getUserAgent(httpServletRequest);
    }

    public static String getClientIP() {
        HttpServletRequest httpServletRequest = getRequest();
        if (httpServletRequest == null) {
            return null;
        }
        return ServletUtil.getClientIP(httpServletRequest);
    }

    public static boolean isJsonRequest(ServletRequest httpServletRequest) {
        return StrUtil.startWithIgnoreCase(httpServletRequest.getContentType(), MediaType.APPLICATION_JSON_VALUE);
    }

    public static String getBody(HttpServletRequest httpServletRequest) {
        // 只有在 json 请求在读取,因为只有 CacheRequestBodyFilter 才会进行缓存,支持重复读取
        if (isJsonRequest(httpServletRequest)) {
            return ServletUtil.getBody(httpServletRequest);
        }
        return null;
    }

    public static byte[] getBodyBytes(HttpServletRequest httpServletRequest) {
        // 只有在 json 请求在读取,因为只有 CacheRequestBodyFilter 才会进行缓存,支持重复读取
        if (isJsonRequest(httpServletRequest)) {
            return ServletUtil.getBodyBytes(httpServletRequest);
        }
        return null;
    }

    public static String getClientIP(HttpServletRequest httpServletRequest) {
        return ServletUtil.getClientIP(httpServletRequest);
    }

    public static Map<String, String> getParamMap(HttpServletRequest httpServletRequest) {
        return ServletUtil.getParamMap(httpServletRequest);
    }

    public static Map<String, String> getHeaderMap(HttpServletRequest httpServletRequest) {
        return ServletUtil.getHeaderMap(httpServletRequest);
    }
}
