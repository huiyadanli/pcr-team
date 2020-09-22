package com.huiyadan.pcr.api.bigfun;

import com.huiyadan.pcr.api.utils.HttpUtils;
import lombok.Getter;
import lombok.Setter;
import net.dongliu.requests.Requests;

import java.util.HashMap;
import java.util.Map;

/**
 * @author huiyadanli
 */
public class BigfunRequests {

    private static final BigfunRequests INSTANCE = new BigfunRequests();

    @Getter
    @Setter
    private Map<String, Object> headers;

    @Getter
    @Setter
    private Map<String, Object> cookies;

    private BigfunRequests() {
    }

    public static BigfunRequests cookie(String cookie) {
        INSTANCE.setCookies(HttpUtils.parseCookieStr(cookie));
        Map<String, Object> headers = new HashMap<>();
        headers.put("Host", "www.bigfun.cn");
        headers.put("Connection", "keep-alive");
        headers.put("Pragma", "no-cache");
        headers.put("Cache-Control", "no-cache");
        headers.put("Accept", "application/json, text/plain, */*");
        headers.put("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/84.0.4147.135 Safari/537.36");
        headers.put("Sec-Fetch-Site", "same-origin");
        headers.put("Sec-Fetch-Mode", "cors");
        headers.put("Sec-Fetch-Dest", "empty");
        headers.put("Referer", "https://www.bigfun.cn/tools/pcrteam/d_report");
        headers.put("Accept-Encoding", "gzip, deflate, br");
        headers.put("Accept-Language", "zh-CN,zh;q=0.9,en;q=0.8");
        headers.put("x-csrf-token", "mAfICY88-uxw-Wn5nIrjblMHD9GiI4jjB1bo");
        INSTANCE.setHeaders(headers);
        return INSTANCE;
    }

    public String get(String url) {
        return Requests.get(url).headers(headers).cookies(cookies).send().readToText();
    }
}
