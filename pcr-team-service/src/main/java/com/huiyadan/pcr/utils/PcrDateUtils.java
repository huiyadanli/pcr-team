package com.huiyadan.pcr.utils;

import org.apache.commons.lang3.time.DateUtils;
import org.apache.commons.lang3.time.FastDateFormat;

import java.util.Calendar;
import java.util.Date;

/**
 * 兰德索尔特有时间工具类
 *
 * @author huiyadanli
 */
public class PcrDateUtils {

    public static Date getToday() {
        Date now = new Date();

        // 获取当前几时
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(now);
        int h = calendar.get(Calendar.HOUR_OF_DAY);

        Date today = DateUtils.truncate(now, Calendar.DAY_OF_MONTH);
        if (h < 5) {
            return DateUtils.addDays(today, -1);
        } else {
            return today;
        }
    }

    public static String getTodayStr() {
        return FastDateFormat.getInstance("yyyy-MM-dd").format(getToday());
    }

//    public static void main(String[] args) throws ParseException {
//        Date now = DateUtils.parseDate("2020-09-24 04:00:00","yyyy-MM-dd HH:mm:ss");
//        // 获取当前几时
//        Calendar calendar = Calendar.getInstance();
//        calendar.setTime(now);
//        int h = calendar.get(Calendar.HOUR_OF_DAY);
//
//        Date today = DateUtils.truncate(now, Calendar.DAY_OF_MONTH);
//        if (h < 5) {
//            System.out.println(FastDateFormat.getInstance("yyyy-MM-dd").format(DateUtils.addDays(today, -1)));
//        } else {
//            System.out.println(FastDateFormat.getInstance("yyyy-MM-dd").format(today));
//        }
//    }
}
