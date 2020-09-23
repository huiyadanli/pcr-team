package com.huiyadan.pcr.executor;

import com.huiyadan.pcr.dao.model.DamageEntity;
import com.huiyadan.pcr.service.DayReportService;
import com.huiyadan.pcr.tool.BossInfo;
import com.huiyadan.pcr.tool.GuildMemberInfo;
import com.huiyadan.pcr.utils.PcrDateUtils;
import lombok.extern.slf4j.Slf4j;
import net.mamoe.mirai.Bot;
import net.mamoe.mirai.contact.ContactList;
import net.mamoe.mirai.contact.Group;
import net.mamoe.mirai.contact.Member;
import net.mamoe.mirai.message.data.At;
import net.mamoe.mirai.message.data.MessageChain;
import net.mamoe.mirai.message.data.MessageUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.apache.commons.lang3.time.FastDateFormat;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.text.MessageFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * 催刀执行者
 *
 * @author huiyadanli
 */
@Slf4j
@Component
public class AttackTaskExecutor {

    @Autowired
    private Bot bot;

    @Value("${bot.group}")
    private Long qqGroupId;

    @Autowired
    private DayReportService dayReportService;

    /**
     * 提醒出刀 催刀
     */
    public void urge() {
        urge(null);
    }

    /**
     * 提醒出刀 催刀
     *
     * @param group
     */
    public void urge(Group group) {
        // 获取当天日期
        String dateStr = PcrDateUtils.getTodayStr();
//        dateStr = "2020-08-29"; // test code
        Map<String, Double> map = dayReportService.getAllMemberAttackNum(dateStr);
        // 未出完刀的
        Map<String, Double> incomplete = new HashMap<>();
        for (String nickname : GuildMemberInfo.getAllGameNicknames()) {
            Double d = map.get(nickname);
            if (d == null) {
                incomplete.put(nickname, 0.0);
            } else if (d < 3) {
                incomplete.put(nickname, d);
            }
        }
        // 拼接消息
        if (incomplete.size() > 0) {
            ContactList<Member> groupMembers = bot.getGroup(qqGroupId).getMembers(); // 获取所有群成员
            MessageChain chain = MessageUtils.newChain("当前未出刀情况：\n");
            for (Map.Entry<String, Double> entry : incomplete.entrySet()) {
                // 游戏昵称与QQ号的映射 GuildMemberInfo.getQQByGameNickname
                Long qqId = GuildMemberInfo.getQQByGameNickname(entry.getKey());
                if (qqId != null) {
                    chain = chain.plus(new At(groupMembers.get(qqId))).plus(attackStatusMsg(3 - entry.getValue()));
                } else {
                    chain = chain.plus(entry.getKey() + " ").plus(attackStatusMsg(3 - entry.getValue()));
                }

            }
            if (group == null) {
                bot.getGroup(qqGroupId).sendMessage(chain);
            } else {
                group.sendMessage(chain);
            }
        }
    }

    /**
     * 通过未出刀数生成提醒文案
     *
     * @param n 未出刀数量 0.5为补偿刀未出
     * @return
     */
    private String attackStatusMsg(double n) {
        if (n == 0.5) {
            return "剩余1刀补偿刀未出";
        }
        int k = (int) n;
        String template = "剩余%s刀未出\n";
        if (n - (int) n == 0.5) {
            k++;
            template = "剩余%s刀未出，其中1刀为补偿刀\n";
        }
        return String.format(template, k);
    }

    public void printAttackNumStatus(Group group) {
        // 获取所有出刀情况
        String dateStr = FastDateFormat.getInstance("yyyy-MM-dd").format(DateUtils.truncate(new Date(), Calendar.DAY_OF_MONTH));
//        dateStr = "2020-08-29"; // test code
        Map<String, Double> map = dayReportService.getAllMemberAttackNum(dateStr);
        int sum = 0;
        int reimburseNum = 0;
        for (Double value : map.values()) {
            sum = sum + value.intValue(); // 舍去0.5, 因为补偿刀也算未出的一刀
            // 计算补偿刀数
            if (value - value.intValue() == 0.5) {
                reimburseNum++;
            }
        }
        // 拼接消息
        String msg;
        if (reimburseNum != 0) {
            msg = MessageFormat.format("当前已出{0}刀，剩余{1}刀，其中有{2}刀补偿刀。", sum, 90 - sum, reimburseNum);
        } else {
            msg = MessageFormat.format("当前已出{0}刀，剩余{1}刀。", sum, 90 - sum);
        }
        if (sum == 90) {
            msg = "今日90刀已出完！";
        }
        if (group == null) {
            bot.getGroup(qqGroupId).sendMessage(msg);
        } else {
            group.sendMessage(msg);
        }
    }

    public void printBossStatus(Group group) {
        DamageEntity latest = dayReportService.getLatesttDamageEntity();
        if (latest != null) {
            Integer hp = dayReportService.getBossRemainHp(latest);
            // 拼接消息
            String msg;
            if (hp == 0) {
                int n = latest.getBossNum() + 1; // boss 被击败了, 下一位
                msg = MessageFormat.format("现在{0}周目，新鲜出炉的{1}号boss【{2}】\n生命值{3}", latest.getLapNum(), n, BossInfo.getBossName(n), BossInfo.getHp(n));
            } else {
                msg = MessageFormat.format("现在{0}周目，{1}号boss【{2}】\n生命值{3}", latest.getLapNum(), latest.getBossNum(), latest.getBossName(), hp);
            }
            group.sendMessage(msg);
        } else {
            group.sendMessage("查询最近一次伤害数据异常！");
        }
    }
}
