package com.huiyadan.pcr.tool;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 公会成员信息配置相关
 * - QQ与游戏昵称的对应配置
 *
 * @author huiyadanli
 */
@Slf4j
@Component
public class GuildMemberInfo {

    private final static Map<String, Long> qqWithNickname = new HashMap<>();

    /**
     * QQ与游戏昵称的对应配置
     */
    @Value("${pcr.member-info-path}")
    private String path;

    @PostConstruct
    public void init() {
        try {
            List<String> lines = FileUtils.readLines(new File(path), "UTF-8");
            for (String line : lines) {
                String[] items = StringUtils.split(line, ",");
                qqWithNickname.put(StringUtils.trim(items[1]), Long.valueOf(StringUtils.trim(items[0])));
            }
        } catch (IOException e) {
            log.error("公会成员数据初始化失败！", e);
        }
    }

    public static Long getQQByGameNickname(String nickname) {
        return qqWithNickname.get(nickname);
    }

}
