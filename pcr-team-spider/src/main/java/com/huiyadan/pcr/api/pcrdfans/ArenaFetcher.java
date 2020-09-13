package com.huiyadan.pcr.api.pcrdfans;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import net.dongliu.requests.Requests;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 国服jjc  pcrdfans api
 *
 * @author huiyadanli
 */
@Slf4j
@Component
public class ArenaFetcher {

    private static final ArenaFetcher INSTANCE = new ArenaFetcher();

    private static final String URL = "https://api.pcrdfans.com/x/v1/search";

    @Value("${jjc.pcrdfans-key}")
    private String authKey;

    private Map<String, Object> headers = new HashMap<>();

    @PostConstruct
    private void init() {
        headers.put("user-agent", "yobot");
        headers.put("authorization", authKey);
    }

    public String post(List<Integer> list) {
        Map<String, Object> data = new HashMap<>();
        data.put("_sign", "a");
        data.put("def", list);
        data.put("nonce", "a");
        data.put("page", 1);
        data.put("sort", 1);
        data.put("ts", System.currentTimeMillis() / 1000);
        data.put("region", 2); // 国服
        ObjectMapper mapper = new ObjectMapper();
        try {
            return Requests.post(URL).headers(headers).body(mapper.writeValueAsString(data)).send().readToText();
        } catch (Exception e) {
            log.error("请求pcrdfans时发生异常", e);
            return null;
        }
    }
}
