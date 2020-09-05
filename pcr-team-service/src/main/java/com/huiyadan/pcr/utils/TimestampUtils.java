package com.huiyadan.pcr.utils;

import org.apache.commons.lang3.time.FastDateFormat;

/**
 * 时间戳工具类
 *
 * @author lihh
 */
public class TimestampUtils {

    public static int getNow() {
        long now = System.currentTimeMillis() / 1000;
        return (int) now;
    }

    public static String toDateStr(Integer time) {
        return FastDateFormat.getInstance("yyyy-MM-dd").format(time * 1000);
    }

    public static String toDatetimeStr(Integer time) {
        return FastDateFormat.getInstance("yyyy-MM-dd HH:mm:ss").format(time * 1000);
    }
}
