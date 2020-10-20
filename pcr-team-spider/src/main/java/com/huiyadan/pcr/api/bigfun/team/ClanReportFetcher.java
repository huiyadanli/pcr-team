package com.huiyadan.pcr.api.bigfun.team;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.huiyadan.pcr.api.bigfun.BigfunRequests;
import com.huiyadan.pcr.api.bigfun.team.model.ClanViewer;
import com.huiyadan.pcr.api.bigfun.team.model.Response;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * 行会总表
 *
 * @author huiyadanli
 */
@Slf4j
@Component
public class ClanReportFetcher {

    @Value("${bigfun.cookies}")
    private String cookies;

    @Value("${bigfun.csrfToken}")
    private String csrfToken;

    private final static String URL = "https://www.bigfun.cn/api/feweb?target=gzlj-clan-collect-report%2Fa";

    public List<ClanViewer> get() {
        try {
            String resp = BigfunRequests.cookie(cookies, csrfToken).get(URL);
            ObjectMapper mapper = new ObjectMapper();
            Response response = mapper.readValue(resp, Response.class);
            if (response != null && response.getData() != null && CollectionUtils.isNotEmpty(response.getData().getData())) {
                return response.getData().getData();
            } else {
                log.warn("从bigfun获取行会总表时,成员返回为空");
            }
        } catch (Exception e) {
            log.error("从bigfun获取行会总表时异常", e);
        }
        return null;
    }
}
