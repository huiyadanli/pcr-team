package com.huiyadan.pcr.service;

import com.huiyadan.pcr.api.bigfun.day.DayReportFetcher;
import com.huiyadan.pcr.api.bigfun.day.model.Member;
import com.huiyadan.pcr.dao.mapper.DamageEntityMapper;
import com.huiyadan.pcr.dao.model.DamageEntity;
import com.huiyadan.pcr.tool.BossInfo;
import com.huiyadan.pcr.utils.BeanTransform;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.apache.commons.lang3.time.FastDateFormat;
import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Example;
import tk.mybatis.mapper.weekend.WeekendSqls;

import java.text.ParseException;
import java.util.*;
import java.util.stream.Collectors;

/**
 * 本期公会战当日出刀数据获取与新增
 *
 * @author huiyadanli
 */
@Slf4j
@Service
public class DayReportService {

    @Autowired
    private DayReportFetcher dayReportFetcher;

    @Autowired
    private DamageEntityMapper damageEntityMapper;

    @Value("${pcr.battle-stage}")
    private Integer stage;

    @Value("${pcr.battle-start-date}")
    private String startDate;

    @Value("${pcr.battle-days:6}")
    private Integer days;

    /**
     * 查询最近一次伤害
     * @return
     */
    public DamageEntity getLatesttDamageEntity() {
        List<DamageEntity> list = damageEntityMapper.selectByExampleAndRowBounds(new Example.Builder(DamageEntity.class)
                .where(WeekendSqls.<DamageEntity>custom()
                        .andEqualTo(DamageEntity::getStage, stage))
                .orderByDesc("attackTime")
                .build(), new RowBounds(0, 1));
        if (list == null || list.size() != 1) {
            log.error("查询最近一次伤害异常");
            return null;
        }
        return list.get(0);
    }


    /**
     * 从数据库获取所有成员出刀数
     *
     * @param dateStr
     * @return 游戏昵称-出刀数
     */
    public Map<String, Double> getAllMemberAttackNum(String dateStr) {
        // 获取今日所有出刀数据
        List<DamageEntity> list = damageEntityMapper.selectByExample(new Example.Builder(DamageEntity.class)
                .where(WeekendSqls.<DamageEntity>custom()
                        .andEqualTo(DamageEntity::getStage, stage)
                        .andEqualTo(DamageEntity::getDate, dateStr))
                .build());
        // 计算出刀数 尾刀和补偿刀算 0.5
        Map<String, Double> map = new HashMap<>();
        for (DamageEntity entity : list) {
            double cnt = 1;
            if (entity.getReimburse() == 1 || entity.getKilled() == 1) {
                cnt = 0.5;
            }
            if (map.containsKey(entity.getGameNickname())) {
                map.put(entity.getGameNickname(), map.get(entity.getGameNickname()) + cnt);
            } else {
                map.put(entity.getGameNickname(), cnt);
            }
        }
        return map;
    }

    /**
     * 计算当前第几刀
     *
     * @param damageEntity
     * @return
     */
    public Integer getAttackNum(DamageEntity damageEntity) {
        // 获取当前成员今日所有出刀数据
        List<DamageEntity> list = damageEntityMapper.selectByExample(new Example.Builder(DamageEntity.class)
                .where(WeekendSqls.<DamageEntity>custom()
                        .andEqualTo(DamageEntity::getStage, damageEntity.getStage())
                        .andEqualTo(DamageEntity::getDate, damageEntity.getDate())
                        .andEqualTo(DamageEntity::getGameNickname, damageEntity.getGameNickname()))
                .orderByAsc("attackTime")
                .build());
        // 计算出刀数
        int i = 0;
        for (DamageEntity entity : list) {
            if (entity.getReimburse() == 0) {
                i++;
            }
            if (compareDamageEntity(entity, damageEntity)) {
                break;
            }
        }
        damageEntity.setAttackNum(i);
        return i;
    }

    /**
     * 计算 boss 剩余血量
     *
     * @param damageEntity
     * @return
     */
    public Integer getBossRemainHp(DamageEntity damageEntity) {
        Integer sumDamage = damageEntityMapper.sumDamage(damageEntity);
        Integer bossHp = BossInfo.getHp(damageEntity.getStage(), damageEntity.getBossNum());
        if (sumDamage != null && bossHp != null) {
            damageEntity.setRemainHp(bossHp - sumDamage);
            return damageEntity.getRemainHp();
        } else {
            log.warn("boss 剩余血量计算失败: {} - {}", bossHp, sumDamage);
            return -9527;
        }
    }

    /**
     * 拉取当日所有数据
     * <p>
     * 比对数据库(damage)数据
     * 并新增数据库没有的数据
     *
     * @param date 日期,一般是当天
     * @return 返回本次新增的数据用于群内通知
     */
    @Transactional
    public List<DamageEntity> add(Date date) {
        // 日期在会战期间的校验
        String dateStr = FastDateFormat.getInstance("yyyy-MM-dd").format(date);
        if (!isInTeamWar(date)) {
            log.error("{} 不在公会战期间", dateStr);
            return null;
        }
        // 获取新数据并新增
        List<DamageEntity> list = getNewData(dateStr);
        if (CollectionUtils.isNotEmpty(list)) {
            int sum = 0;
            for (DamageEntity damageEntity : list) {
                sum += damageEntityMapper.insertSelective(damageEntity);
            }
            log.info("{} 新增 {} 数据", dateStr, sum);
            return list;
        } else {
            return null;
        }
    }

    /**
     * 拉取当日所有数据
     * <p>
     * 比对数据库(damage)数据,得到新增数据
     *
     * @param dateStr 日期(yyyy-MM-dd),一般是当天
     * @return 返回本次新增的数据用于群内通知
     */
    public List<DamageEntity> getNewData(String dateStr) {
        try {
            // 从 bigfun 下载该日期数据
            List<DamageEntity> damageListFromWeb = getSortedDataFromWeb(dateStr);
            if (CollectionUtils.isEmpty(damageListFromWeb)) {
                return null;
            }

            // 获取当前表中数据
            List<DamageEntity> damageListFromDatabase = getSortedDataFromDatabase(dateStr);
            // 当前数据库无数据时,需要新增所有拉取的数据
            if (CollectionUtils.isEmpty(damageListFromDatabase)) {
                return damageListFromWeb;
            }

            if (damageListFromWeb.size() < damageListFromDatabase.size()) {
                log.warn("当前数据库比 bigfun 出刀数量要多，请查看是否存在重复数据！");
                return null;
            }
            if (damageListFromWeb.size() == damageListFromDatabase.size()) {
                log.info("当前数据库和 bigfun 出刀数量一致");
            }
            // 循环比较已有数据
            for (int i = 0; i < damageListFromDatabase.size(); i++) {
                DamageEntity fromWeb = damageListFromWeb.get(i);
                DamageEntity fromDatabase = damageListFromDatabase.get(i);
                if (!compareDamageEntity(fromWeb, fromDatabase)) {
                    log.warn("当前数据库已有数据和 bigfun 上数据不一致，请查看是否存在数据遗漏！");
                    return null;
                }
            }
            log.info("当前数据库和 bigfun 出刀数据比对一致");
            // 返回新增数据
            List<DamageEntity> addedList = new ArrayList<>();
            for (int i = damageListFromDatabase.size(); i < damageListFromWeb.size(); i++) {
                addedList.add(damageListFromWeb.get(i));
            }
            return addedList;
        } catch (Exception e) {
            log.error("", e);
            return null;
        }
    }

    private List<DamageEntity> getSortedDataFromWeb(String dateStr) {
        // 下载该日期数据
        List<Member> listFromBigfun = dayReportFetcher.getByDate(dateStr);
        if (CollectionUtils.isEmpty(listFromBigfun)) {
            log.warn("从 bigfun 拉取 {} 数据为空", dateStr);
            return null;
        }
        // 转换为出刀数据并排序
        List<DamageEntity> damageListFromWeb = BeanTransform.toDamageEntity(stage, dateStr, listFromBigfun);
        return damageListFromWeb.stream().sorted(Comparator.comparing(DamageEntity::getAttackTime)).collect(Collectors.toList());
    }

    private List<DamageEntity> getSortedDataFromDatabase(String dateStr) {
        return damageEntityMapper.selectByExample(new Example.Builder(DamageEntity.class)
                .where(WeekendSqls.<DamageEntity>custom()
                        .andEqualTo(DamageEntity::getStage, stage)
                        .andEqualTo(DamageEntity::getDate, dateStr))
                .orderByAsc("attackTime")
                .build());
    }

    private boolean compareDamageEntity(DamageEntity fromWeb, DamageEntity fromDatabase) {
        return fromWeb.getGameNickname().equals(fromDatabase.getGameNickname())
                && fromWeb.getBossName().equals(fromDatabase.getBossName())
                && fromWeb.getDamage().equals(fromDatabase.getDamage())
                && fromWeb.getAttackTime().equals(fromDatabase.getAttackTime());
    }

    private boolean isInTeamWar(Date date) {
        try {
            Date start = DateUtils.parseDate(startDate, "yyyy-MM-dd");
            Date end = DateUtils.addDays(start, days);
            if (date.getTime() >= start.getTime() && date.getTime() <= end.getTime()) {
                return true;
            } else {
                return false;
            }
        } catch (ParseException e) {
            log.error("日期转换失败", e);
            return false;
        }
    }

}
