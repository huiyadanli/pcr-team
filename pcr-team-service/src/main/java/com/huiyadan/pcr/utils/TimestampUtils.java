package com.huiyadan.pcr.utils;

import org.apache.commons.lang3.time.FastDateFormat;

/**
 * 时间戳工具类
 *
 * @author huiyadanli
 */
public class TimestampUtils {

    public static int getNow() {
        long now = System.currentTimeMillis() / 1000;
        return (int) now;
    }

    public static String toDateStr(Integer time) {
        if (time == null) {
            return null;
        }
        return FastDateFormat.getInstance("yyyy-MM-dd").format((long) time * 1000);
    }

    public static String toDatetimeStr(Integer time) {
        if (time == null) {
            return null;
        }
        return FastDateFormat.getInstance("yyyy-MM-dd HH:mm:ss").format((long) time * 1000);
    }

    /**
     * test case
     *
     * @param args
     */
    public static void main(String[] args) {
        System.out.println(toDatetimeStr(1598274559));
    }
}
