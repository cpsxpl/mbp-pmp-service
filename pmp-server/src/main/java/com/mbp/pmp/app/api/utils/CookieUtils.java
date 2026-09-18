package com.mbp.pmp.app.api.utils;

import com.mbp.eng.module.example.service.rpcAuth.RpcAuthService;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class CookieUtils {
    private static final Logger logger = LoggerFactory.getLogger(CookieUtils.class);

    public static final String PRESS_ERP = "press_erp";

    public static final String PRESS_PIN = "press_pin";

    /**
     * 加密Cookie
     *
     * @param rpcAuthService
     * @param httpServletResponse
     * @param pin
     */
    public static void addVPinCookie(String cookieName, RpcAuthService rpcAuthService, HttpServletResponse httpServletResponse, String pin) {
        String cipherPin = "admin"; //rpcAuthService.encryptVirtualPin(pin).getResult();
        Cookie cookie = new Cookie(cookieName, cipherPin);
        cookie.setPath("/");
        cookie.setDomain("xx.com");
        cookie.setMaxAge(-1);
        httpServletResponse.addCookie(cookie);
    }

    /**
     * 解密Cookie
     *
     * @param httpServletRequest
     * @return
     */
    public static String getVirtualPin(String string, RpcAuthService rpcAuthService, HttpServletRequest httpServletRequest) {
        Cookie[] cookies = httpServletRequest.getCookies();
        for (Cookie cookie : cookies) {
            // 解密虚拟登录cookie
            if (cookie.getName().equals(string)) {
                //return authService.decryptVirtualPin(cookie.getValue()).getResult();
            }
        }
        return null;
    }

    /**
     * 获取cookie value
     *
     * @param httpServletRequest
     * @return
     */
    public static String getCookieValue(String string, HttpServletRequest httpServletRequest) {
        Cookie[] cookies = httpServletRequest.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                String name = cookie.getName();
                if (StringUtils.isNotEmpty(name) && name.equals(string)) {
                    String value = cookie.getValue();
                    return value;
                }
            }
        }
        return null;
    }
}
