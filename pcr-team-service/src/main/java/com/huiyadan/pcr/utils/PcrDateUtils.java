package com.huiyadan.pcr.utils;

import org.apache.commons.lang3.time.DateUtils;
import org.apache.commons.lang3.time.FastDateFormat;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.Calendar;
import java.util.Date;

/**
 * 兰德索尔特有时间工具类
 *
 * @author huiyadanli
 */
@Component
public class PcrDateUtils {

    public static final Logger log = LoggerFactory.getLogger(PcrDateUtils.class);

    @Value("${pcr.battle-start-date}")
    private String startDate;

    @Value("${pcr.battle-days:6}")
    private Integer days;

    private static String startDateStatic;

    private static Integer daysStatic;

    @PostConstruct
    public void init() {
        startDateStatic = startDate;
        daysStatic = days;
    }

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


    public static boolean isInClanBattle(Date now) {
        try {
            Date start = DateUtils.parseDate(startDateStatic, "yyyy-MM-dd");
            Date end = DateUtils.addDays(start, daysStatic);
            if (now.getTime() >= start.getTime() && now.getTime() <= end.getTime()) {
                return true;
            } else {
                return false;
            }
        } catch (Exception e) {
            log.error("日期转换失败", e);
            return false;
        }
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
