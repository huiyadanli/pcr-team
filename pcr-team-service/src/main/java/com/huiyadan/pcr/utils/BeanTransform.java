package com.huiyadan.pcr.utils;

import com.google.common.collect.Lists;
import com.huiyadan.pcr.api.bigfun.day.model.Damage;
import com.huiyadan.pcr.api.bigfun.day.model.Member;
import com.huiyadan.pcr.dao.model.DamageEntity;
import com.huiyadan.pcr.dao.model.DamageHistoryEntity;
import com.huiyadan.pcr.tool.BossInfo;
import com.huiyadan.pcr.tool.ScoreCalculator;

import java.util.List;

/**
 * @author lihh
 */
public class BeanTransform {

    public static DamageHistoryEntity toDamageHistoryEntity(Integer stage, String dateStr, Member member, Damage damage) {
        DamageHistoryEntity entity = new DamageHistoryEntity();
        entity.setStage(stage);
        entity.setDate(dateStr);
        entity.setLapNum(damage.getLap_num());
//        entity.setGameId();  // TODO 工会成员配置
        entity.setGameNickname(member.getName());
        entity.setBossNum(BossInfo.getIndex(stage, damage.getBoss_name()));
        entity.setBossName(damage.getBoss_name());
        entity.setDamage(damage.getDamage());
        entity.setScore(ScoreCalculator.compute(damage.getLap_num(), entity.getBossNum(), damage.getDamage()));
        entity.setKilled(damage.getKill());
        entity.setReimburse(damage.getReimburse());
        entity.setAttackTime(damage.getDatetime());
        entity.setCreateTime(TimestampUtils.getNow());
        return entity;

    }

    public static List<DamageHistoryEntity> toDamageHistoryEntity(Integer stage, String dateStr, List<Member> members) {
        List<DamageHistoryEntity> list = Lists.newArrayList();
        for (Member member : members) {
            for (Damage damage : member.getDamage_list()) {
                list.add(toDamageHistoryEntity(stage, dateStr, member, damage));
            }
        }
        return list;
    }

    public static DamageEntity toDamageEntity(Integer stage, String dateStr, Member member, Damage damage) {
        DamageEntity entity = new DamageEntity();
        entity.setStage(stage);
        entity.setDate(dateStr);
        entity.setLapNum(damage.getLap_num());
//        entity.setGameId();  // TODO 工会成员配置
        entity.setGameNickname(member.getName());
        entity.setBossNum(BossInfo.getIndex(stage, damage.getBoss_name()));
        entity.setBossName(damage.getBoss_name());
        entity.setDamage(damage.getDamage());
        entity.setScore(ScoreCalculator.compute(damage.getLap_num(), entity.getBossNum(), damage.getDamage()));
        entity.setKilled(damage.getKill());
        entity.setReimburse(damage.getReimburse());
        entity.setAttackTime(damage.getDatetime());
        entity.setCreateTime(TimestampUtils.getNow());
        return entity;

    }

    public static List<DamageEntity> toDamageEntity(Integer stage, String dateStr, List<Member> members) {
        List<DamageEntity> list = Lists.newArrayList();
        for (Member member : members) {
            for (Damage damage : member.getDamage_list()) {
                list.add(toDamageEntity(stage, dateStr, member, damage));
            }
        }
        return list;
    }
}
