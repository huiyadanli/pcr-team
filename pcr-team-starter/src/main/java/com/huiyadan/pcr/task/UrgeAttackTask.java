package com.huiyadan.pcr.task;

import com.huiyadan.pcr.service.DayReportService;
import net.mamoe.mirai.Bot;
import org.apache.commons.lang3.time.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;

import java.util.Calendar;
import java.util.Date;

/**
 * 群内催刀
 * @author huiyadanli
 */
public class UrgeAttackTask {

    @Autowired
    private Bot bot;

    @Value("${bot.group}")
    private Long qqGroupId;

    @Autowired
    private DayReportService dayReportService;

    @Scheduled(cron = "${task.urge.cron}")
    public void execute() {
        // 获取当天日期
        Date today = DateUtils.truncate(new Date(), Calendar.DAY_OF_MONTH);

        bot.getGroup(qqGroupId).sendMessage("xxx出刀");
    }
}
