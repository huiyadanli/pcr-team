package com.huiyadan.pcr.api.bigfun.boss.model;

import lombok.Getter;
import lombok.Setter;

/**
 * @author huiyadanli
 */
@Getter
@Setter
public class BossNameInfo {
    /**
     * 第一位是分期编号
     * 比如701 702
     */
    private String id;

    /**
     * boss名字
     */
    private String boss_name;
}
