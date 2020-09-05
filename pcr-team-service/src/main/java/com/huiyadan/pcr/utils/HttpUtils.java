package com.huiyadan.pcr.utils;

import java.util.HashMap;
import java.util.Map;

/**
 * @author huiyadanli
 */
public class HttpUtils {

    /**
     * 解析cookie字符串
     *
     * @param cookieStr Chrome 请求的 cookie 字符串
     * @return
     */
    public static Map<String, Object> parseCookieStr(String cookieStr) {
        Map<String, Object> cookies = new HashMap<>();
        String[] cs = cookieStr.split(";");
        for (String c : cs) {
            String[] kv = c.trim().split("=");
            cookies.put(kv[0], kv[1]);
        }
        return cookies;
    }
}
