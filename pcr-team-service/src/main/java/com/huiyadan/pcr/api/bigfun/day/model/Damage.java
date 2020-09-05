package com.huiyadan.pcr.api.bigfun.day.model;

import lombok.Getter;
import lombok.Setter;

/**
 * @author huiyadanli
 */
@Setter
@Getter
public class Damage {
    /**
     * boss 名称
     */
    private String boss_name;

    /**
     * 周目数
     */
    private Integer lap_num;

    /**
     * 伤害
     */
    private Integer damage;

    /**
     * 是否击杀boss
     * 0 未击杀 1 击杀
     */
    private Integer kill;

    /**
     * 0正常刀 1补偿刀
     */
    private Integer reimburse;

    /**
     * 出刀时间 （时间戳：秒）
     */
    private Integer datetime;

}
