package com.huiyadan.pcr.task;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.huiyadan.pcr.dao.model.DamageEntity;
import com.huiyadan.pcr.service.DayReportService;
import com.huiyadan.pcr.utils.TimestampUtils;
import lombok.extern.slf4j.Slf4j;
import net.mamoe.mirai.Bot;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.apache.commons.text.StringSubstitutor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.*;
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
        Map<String, Object> map = new HashMap<>();
        map.put("attackTime", TimestampUtils.toDatetimeStr(damage.getAttackTime()));
        map.put("gameNickname", damage.getGameNickname());
        map.put("bossNum", damage.getBossNum());
        map.put("bossName", damage.getBossName());
        map.put("damage", damage.getDamage());
        StringSubstitutor ss = new StringSubstitutor(map);
        bot.getGroup(qqGroupId).sendMessage(ss.replace(msgTemplate));
    }
}
