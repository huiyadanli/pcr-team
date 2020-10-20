package com.huiyadan.pcr.api.bigfun.boss;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.huiyadan.pcr.api.bigfun.BigfunRequests;
import com.huiyadan.pcr.api.bigfun.boss.model.BossNameInfo;
import com.huiyadan.pcr.api.bigfun.boss.model.Response;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * BOSS报表数据获取（主要获取boss信息）
 *
 * @author huiyadanli
 */
@Slf4j
@Component
public class BossReportFetcher {

    @Value("${bigfun.cookies}")
    private String cookies;

    @Value("${bigfun.csrfToken}")
    private String csrfToken;

    private final static String URL = "https://www.bigfun.cn/api/feweb?target=gzlj-clan-boss-report-collect%2Fa";

    public List<BossNameInfo> getBossInfo() {
        try {
            String resp = BigfunRequests.cookie(cookies, csrfToken).get(URL);
            ObjectMapper mapper = new ObjectMapper();
            Response response = mapper.readValue(resp, Response.class);
            if (response != null && response.getData()!=null && CollectionUtils.isNotEmpty(response.getData().getBoss_list())) {
                return response.getData().getBoss_list();
            } else {
                log.warn("从bigfun获取BOSS报表中的BOSS信息,返回为空");
            }
        } catch (Exception e) {
            log.error("从bigfun获取BOSS报表时异常", e);
        }
        return null;
    }
}
