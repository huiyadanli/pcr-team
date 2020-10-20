package com.huiyadan.pcr.api.bigfun.boss.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * @author huiyadanli
 */
@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class BossReport {

    /**
     * 团队战当期名字
     */
    private String name;

    private List<BossNameInfo> boss_list;
}
