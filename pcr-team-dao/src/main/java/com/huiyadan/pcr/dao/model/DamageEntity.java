package com.huiyadan.pcr.dao.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Table;

@Getter
@Setter
@Table(name = "damage")
public class DamageEntity {
    @Column(name = "stage")
    private Integer stage;

    @Column(name = "date")
    private String date;

    @Column(name = "lap_num")
    private Integer lapNum;

    @Column(name = "game_id")
    private String gameId;

    @Column(name = "game_nickname")
    private String gameNickname;

    @Column(name = "boss_num")
    private Integer bossNum;

    @Column(name = "boss_name")
    private String bossName;

    @Column(name = "damage")
    private Integer damage;

    @Column(name = "score")
    private Integer score;

    @Column(name = "killed")
    private Integer killed;

    @Column(name = "reimburse")
    private Integer reimburse;

    @Column(name = "attack_time")
    private Integer attackTime;

    @Column(name = "create_time")
    private Integer createTime;
}