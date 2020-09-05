package com.huiyadan.pcr.dao.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Table;

@Getter
@Setter
@Table(name = "damage")
public class DamageEntity {
    /**
     * 第几期
     */
    @Column(name = "stage")
    private Integer stage;

    /**
     * 出刀日期
     */
    @Column(name = "date")
    private String date;

    /**
     * 周目数
     */
    @Column(name = "lap_num")
    private Integer lapNum;

    /**
     * 游戏ID
     */
    @Column(name = "game_id")
    private String gameId;

    /**
     * 游戏中昵称
     */
    @Column(name = "game_nickname")
    private String gameNickname;

    /**
     * boss 编号
     */
    @Column(name = "boss_num")
    private Integer bossNum;

    /**
     * boss 名称
     */
    @Column(name = "boss_name")
    private String bossName;

    /**
     * 伤害
     */
    @Column(name = "damage")
    private Integer damage;

    /**
     * 积分
     */
    @Column(name = "score")
    private Integer score;

    /**
     * 是否击杀boss
     * 0 未击杀 1 击杀
     */
    @Column(name = "killed")
    private Integer killed;

    /**
     * 是否是补偿刀
     * 0正常刀 1补偿刀
     */
    @Column(name = "reimburse")
    private Integer reimburse;

    /**
     * 出刀时间（时间戳）
     */
    @Column(name = "attack_time")
    private Integer attackTime;

    /**
     * 数据库创建时间（时间戳）
     */
    @Column(name = "create_time")
    private Integer createTime;
}