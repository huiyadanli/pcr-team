package com.huiyadan.pcr.tool;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.ImmutableBiMap;
import com.huiyadan.pcr.tool.boss.AllStages;
import com.huiyadan.pcr.tool.boss.StageBossInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.Map;

/**
 * @author huiyadanli
 */
@Slf4j
@Component
public class BossInfo {

    private static AllStages all;

    private static Integer currStage;

    /**
     * 默认 boss 血量
     */
    private static Map<Integer, Integer> defaultHp = ImmutableBiMap.of(
            1, 6000000,
            2, 8000000,
            3, 10000000,
            4, 12000000,
            5, 20000000);

    @Value("${pcr.boss-info}")
    private String bossInfoJson;

    @Value("${pcr.battle-stage}")
    private Integer battleStage;

    /**
     * 解析配置文件中的 json 配置
     */
    @PostConstruct
    public void init() {
        try {
            all = new ObjectMapper().readValue(bossInfoJson, AllStages.class);
            currStage = battleStage;
        } catch (JsonProcessingException e) {
            log.error("解析boss信息配置(pcr.boss-info)失败", e);
        }
    }

    /**
     * 获取 boss 编号
     *
     * @param stage    第几期
     * @param bossName boss 名称
     * @return
     */
    public static Integer getIndex(Integer stage, String bossName) {
        for (StageBossInfo stageBossInfo : all.getStages()) {
            if (stageBossInfo.getStage().intValue() == stage.intValue()) {
                for (int i = 0; i < 5; i++) {
                    if (stageBossInfo.getBossNames().get(i).equals(bossName)) {
                        return i + 1;
                    }
                }
            }
        }
        return null;
    }

    public static Integer getIndex(String bossName) {
        return getIndex(currStage, bossName);
    }

    /**
     * 获取 boss 名称
     *
     * @param stage   第几期
     * @param bossNum boss 编号
     * @return
     */
    public static String getBossName(Integer stage, Integer bossNum) {
        for (StageBossInfo stageBossInfo : all.getStages()) {
            if (stageBossInfo.getStage().intValue() == stage.intValue()) {
                return stageBossInfo.getBossNames().get(bossNum - 1);
            }
        }
        return null;
    }

    public static String getBossName(Integer bossNum) {
        return getBossName(currStage, bossNum);
    }

    /**
     * 通名称获取 boss 血量
     *
     * @param stage    第几期
     * @param bossName boss 名称
     * @return
     */
    public static Integer getHp(Integer stage, String bossName) {
        Integer bossNum = getIndex(stage, bossName);
        return getHp(stage, bossNum);
    }

    public static Integer getHp(String bossName) {
        return getHp(currStage, bossName);
    }

    public static Integer getHp(Integer bossNum) {
        return getHp(currStage, bossNum);
    }


    /**
     * 通过编号获取 boss 血量
     *
     * @param stage   第几期
     * @param bossNum boss 编号
     * @return
     */
    public static Integer getHp(Integer stage, Integer bossNum) {
        for (StageBossInfo stageBossInfo : all.getStages()) {
            if (stageBossInfo.getStage().intValue() == stage.intValue()) {
                if (stageBossInfo.getBossHps() == null) {
                    return getDefaultHp(bossNum);
                } else {
                    return stageBossInfo.getBossHps().get(bossNum - 1);
                }
            }
        }
        return getDefaultHp(bossNum);
    }

    /**
     * 通过编号获取默认 boss 血量
     *
     * @param bossNum boss 编号
     * @return
     */
    public static Integer getDefaultHp(Integer bossNum) {
        return defaultHp.get(bossNum);
    }
}
