package com.huiyadan.pcr.tool;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.huiyadan.pcr.tool.boss.AllStages;
import com.huiyadan.pcr.tool.boss.StageBossInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

/**
 * @author lihh
 */
@Slf4j
@Component
public class BossInfo {

    private static AllStages all;

    @Value("${pcr.boss-info}")
    private String bossInfoJson;

    @PostConstruct
    public void init() {
        try {
            all = new ObjectMapper().readValue(bossInfoJson, AllStages.class);
        } catch (JsonProcessingException e) {
            log.error("解析boss信息配置(pcr.boss-info)失败", e);
        }
    }

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

}
