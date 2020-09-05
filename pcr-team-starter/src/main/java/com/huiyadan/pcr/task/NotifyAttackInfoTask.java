package com.huiyadan.pcr.task;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.huiyadan.pcr.dao.model.DamageEntity;
import com.huiyadan.pcr.service.DayReportService;
import com.huiyadan.pcr.utils.TimestampUtils;
import lombok.extern.slf4j.Slf4j;
import net.mamoe.mirai.Bot;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.text.MessageFormat;
import java.util.Calendar;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 群内自动报刀
 *
 * @author huiyadanli
 */
@Slf4j
@Component
public class NotifyAttackInfoTask {

    @Autowired
    private Bot bot;

    @Value("${bot.group}")
    private Long qqGroupId;

    @Value("${task.notify.msg}")
    private String msgTemplate;

    @Autowired
    private DayReportService dayReportService;

    @Scheduled(cron = "${task.notify.cron}")
    public void execute() {
        try {
            ObjectMapper mapper = new ObjectMapper();
            // 获取当天日期
            Date today = DateUtils.truncate(new Date(), Calendar.DAY_OF_MONTH);
            // DateUtils.parseDate("2020-08-29","yyyy-MM-dd")
            List<DamageEntity> list = dayReportService.add(DateUtils.parseDate("2020-08-29", "yyyy-MM-dd"));
            if (CollectionUtils.isNotEmpty(list)) {
                list = list.stream().sorted(Comparator.comparing(DamageEntity::getAttackTime)).collect(Collectors.toList());
                for (DamageEntity damageEntity : list) {
                    sendDamageMessage(damageEntity);
                }
            } else {
                log.info("自动报刀定时任务: 无新增数据");
            }
        } catch (Exception e) {
            log.error("自动报刀定时任务: 执行异常", e);
        }
    }

    private void sendDamageMessage(DamageEntity damage) {
        String msg = MessageFormat.format(msgTemplate,
                TimestampUtils.toDatetimeStr(damage.getAttackTime()),
                damage.getGameNickname(),
                damage.getBossNum(),
                damage.getBossName(),
                damage.getDamage());
        bot.getGroup(qqGroupId).sendMessage(msg);
    }
}
