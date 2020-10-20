package com.huiyadan.pcr.tool;

import com.huiyadan.pcr.api.bigfun.team.ClanReportFetcher;
import com.huiyadan.pcr.api.bigfun.team.model.ClanViewer;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
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
public class ClanMemberInfo {

    private final static Map<String, Long> nicknameWithQQ = new HashMap<>();

    private final static Map<String, Long> uidWithQQ = new HashMap<>();

    @Autowired
    private ClanReportFetcher clanReportFetcher;

    /**
     * QQ与游戏昵称的对应配置
     */
    @Value("${pcr.member-info-path}")
    private String path;

    @PostConstruct
    public void init() {

        // 从bifun获取公会成员数据  uid+昵称
        List<ClanViewer> nicknameWithUid = null;
        try {
            nicknameWithUid = clanReportFetcher.get();
        } catch (Exception e) {
            log.error("从bifun获取公会成员数据时发生异常", e);
            return;
        }

        // 从本地获取 qq+uid数据
        try {
            List<String> lines = FileUtils.readLines(new File(path), "UTF-8");
            for (String line : lines) {
                String[] items = StringUtils.split(line, ",");
                uidWithQQ.put(StringUtils.trim(items[1]), Long.valueOf(StringUtils.trim(items[0])));
            }
        } catch (IOException e) {
            log.error("公会成员QQ号与UID映射数据初始化失败！", e);
            return;
        }

        // 转换为 昵称对应qq的数据
        if (CollectionUtils.isNotEmpty(nicknameWithUid) && uidWithQQ.size() > 0) {
            for (ClanViewer clanViewer : nicknameWithUid) {
                nicknameWithQQ.put(clanViewer.getUsername(), uidWithQQ.get(clanViewer.getViewer_id()));
            }
        }
    }

    public static Long getQQByGameNickname(String nickname) {
        return nicknameWithQQ.get(nickname);
    }

    public static List<String> getAllGameNicknames() {
        return new ArrayList<>(nicknameWithQQ.keySet());
    }

}
