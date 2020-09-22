package com.huiyadan.pcr.api.bigfun.day;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.huiyadan.pcr.api.bigfun.day.model.Response;
import com.huiyadan.pcr.api.bigfun.BigfunRequests;
import com.huiyadan.pcr.api.bigfun.day.model.Member;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * 工会日表数据获取（个人伤害积分表）
 *
 * @author huiyadanli
 */
@Slf4j
@Component
public class DayReportFetcher {

    @Value("${bigfun.cookies}")
    private String cookies;

    private final static String URL = "https://www.bigfun.cn/api/feweb?target=gzlj-clan-day-report%2Fa&date={0}&page={1}&size=30";

    /**
     * @param date 2020-08-26
     * @return
     */
    public List<Member> getByDate(String date) {
        List<Member> members = new ArrayList<>();

        // 多页 上限4页
//        for (int i = 1; i < 5; i++) {
        try {
            String url = MessageFormat.format(URL, date, 1);
            String resp = BigfunRequests.cookie(cookies).get(url);
            log.info("日期 {} 页码 {} {}", date, 1, resp);
            ObjectMapper mapper = new ObjectMapper();
            Response response = mapper.readValue(resp, Response.class);
            if (response != null && CollectionUtils.isNotEmpty(response.getData())) {
                members.addAll(response.getData());
            }
        } catch (Exception e) {
            log.error("从bigfun获取工会日表时异常", e);
        }
//        }
        return members;
    }
}
