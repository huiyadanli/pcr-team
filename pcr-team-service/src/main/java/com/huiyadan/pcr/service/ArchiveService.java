package com.huiyadan.pcr.service;

import com.huiyadan.pcr.api.bigfun.day.DayReportFetcher;
import com.huiyadan.pcr.api.bigfun.day.model.Member;
import com.huiyadan.pcr.dao.mapper.DamageEntityMapper;
import com.huiyadan.pcr.dao.mapper.DamageHistoryEntityMapper;
import com.huiyadan.pcr.dao.model.DamageHistoryEntity;
import com.huiyadan.pcr.utils.BeanTransform;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.time.DateUtils;
import org.apache.commons.lang3.time.FastDateFormat;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

/**
 * 归档本期公会战数据
 *
 * @author huiyadanli
 */
@Slf4j
@Service
public class ArchiveService {

    @Autowired
    private DayReportFetcher dayReportFetcher;

    @Autowired
    private DamageEntityMapper damageEntityMapper;

    @Autowired
    private DamageHistoryEntityMapper damageHistoryEntityMapper;

    @Value("${pcr.battle-stage}")
    private Integer stage;

    @Value("${pcr.battle-start-date}")
    private String startDate;

    @Value("${pcr.battle-days:6}")
    private Integer days;


    /**
     * 从数据库归档
     * damage -> damage_history
     */
    @Transactional
    public void archiveFromDatabase() {
        archiveFromDatabase(stage);
    }

    /**
     * 归档第几期的数据
     *
     * @param stage
     */
    @Transactional
    public void archiveFromDatabase(Integer stage) {
        int an = damageHistoryEntityMapper.insertFromDamageTable(stage);
        int dn = damageEntityMapper.deleteByStage(stage);
        log.info("第{}期数据从数据库归档完毕,出刀历史表新增{}条,出刀当前表删除{}条", stage, an, dn);
    }

    /**
     * 拉取本期所有数据（来自于配置）
     * bigfun d_report -> damage_history
     */
    @Transactional
    public void archiveFromBigfun() {
        archiveFromBigfunByStage(stage, startDate);
    }


    /**
     * 从 bigfun 拉取一个分期所有日表数据归档
     * @param stage        分期
     * @param startDateStr 起始时间
     */
    @Transactional
    public void archiveFromBigfunByStage(Integer stage, String startDateStr) {
        try {
            Date start = DateUtils.parseDate(startDateStr, "yyyy-MM-dd");
            // 会战总共6天
            for (int i = 0; i < days; i++) {
                Date curr = DateUtils.addDays(start, i);
                String currDateStr = FastDateFormat.getInstance("yyyy-MM-dd").format(curr);
                archiveFromBigfunByDate(stage, currDateStr);
            }
            log.info("第{}期,起始时间 {} ,从 bigfun 下载数据并归档完成", stage, startDateStr);
        } catch (Exception e) {
            log.error("从 bigfun 拉取本期所有日表数据归档时异常", e);
        }

    }

    /**
     * 按日期从 bigfun 拉取日表数据归档
     *
     * @param dateStr 日期 yyyy-MM-dd
     */
    @Transactional
    public void archiveFromBigfunByDate(Integer stage, String dateStr) {
        List<Member> listFromBigfun = dayReportFetcher.getByDate(dateStr);
        List<DamageHistoryEntity> damageList = BeanTransform.toDamageHistoryEntity(stage, dateStr, listFromBigfun);
        for (DamageHistoryEntity damageHistoryEntity : damageList) {
            damageHistoryEntityMapper.insertSelective(damageHistoryEntity);
        }
    }
}
