package com.huiyadan.pcr.data;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.Lists;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author lihh
 */
public class BossMapJson {

    private static final Map<Integer, List<String>> bossInfo;

    static {
        bossInfo = new HashMap<>();
        List<String> s5 = Lists.newArrayList("双足飞龙", "野性狮鹫", "雷电", "狂乱魔熊", "炎吼狮王");
        bossInfo.put(5, s5);
    }

    public static void main(String[] args) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        String json = mapper.writeValueAsString(bossInfo);
        System.out.println(json);
    }
}
