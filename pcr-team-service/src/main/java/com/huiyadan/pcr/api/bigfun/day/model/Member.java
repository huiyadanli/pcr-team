package com.huiyadan.pcr.api.bigfun.day.model;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * 以成员分类的伤害列表
 * @author huiyadanli
 */
@Setter
@Getter
public class Member {

    /**
     * 游戏昵称
     */
    private String name;

    /**
     * 出刀总数
     */
    private Integer number;

    /**
     * 总伤害
     */
    private Integer damage;

    /**
     * 总分数
     */
    private Integer score;

    /**
     * 出刀列表
     */
    private List<Damage> damage_list;
}
