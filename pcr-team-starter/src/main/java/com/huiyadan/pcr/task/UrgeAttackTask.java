package com.huiyadan.pcr.task;

import com.huiyadan.pcr.service.DayReportService;
import net.mamoe.mirai.Bot;
import net.mamoe.mirai.contact.ContactList;
import net.mamoe.mirai.contact.Member;
import net.mamoe.mirai.message.data.At;
import net.mamoe.mirai.message.data.MessageChain;
import net.mamoe.mirai.message.data.MessageUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.apache.commons.lang3.time.FastDateFormat;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;

import java.util.Calendar;
import java.util.Date;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 群内催刀
 *
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
        String dateStr = FastDateFormat.getInstance("yyyy-MM-dd").format(today);
        Map<String, Double> map = dayReportService.getAllMerberAttackNum(dateStr);
        // 未出完刀的
        map = map.entrySet().stream()
                .filter(entry -> entry.getValue() < 3)
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
        // 拼接消息
        if (map.size() > 0) {
            ContactList<Member> groupMembers = bot.getGroup(qqGroupId).getMembers(); // 获取所有群成员
            MessageChain chain = MessageUtils.newChain("当前未出刀情况：\n");
            for (Map.Entry<String, Double> entry : map.entrySet()) {
                // TODO 游戏昵称与QQ号的映射
                chain.plus(new At(groupMembers.get(111111))).plus(attackStatusMsg(3 - entry.getValue()));
            }
            bot.getGroup(qqGroupId).sendMessage(chain);
        }

    }

    /**
     * 通过未出刀数生成提醒文案
     *
     * @param n 未出刀数量 0.5为补偿刀未出
     * @return
     */
    private String attackStatusMsg(double n) {
        int k = (int) n;
        String template = "剩余%s刀未出\n";
        if (n - (int) n == 0.5) {
            k++;
            template = "剩余%s刀未出，其中1刀为补偿刀\n";
        }
        return String.format(template, k);
    }
}
