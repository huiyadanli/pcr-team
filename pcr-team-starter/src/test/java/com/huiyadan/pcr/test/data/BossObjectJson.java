package com.huiyadan.pcr.test.data;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.Lists;
import com.huiyadan.pcr.tool.boss.AllStages;
import com.huiyadan.pcr.tool.boss.StageBossInfo;

/**
 * @author huiyadanli
 */
public class BossObjectJson {

    private static final AllStages all;

    static {
        all = new AllStages();

        StageBossInfo s5 = new StageBossInfo();
        s5.setStage(5);
        s5.setBossNames(Lists.newArrayList("双足飞龙", "野性狮鹫", "雷电", "狂乱魔熊", "炎吼狮王"));
        all.getStages().add(s5);

        StageBossInfo s6 = new StageBossInfo();
        s6.setStage(6);
        s6.setBossNames(Lists.newArrayList("双足飞龙", "野性狮鹫", "独眼巨人", "幽灵领主", "美杜莎"));
        all.getStages().add(s6);

    }

    public static void main(String[] args) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        String json = mapper.writeValueAsString(all);
        System.out.println(json);
    }
}
