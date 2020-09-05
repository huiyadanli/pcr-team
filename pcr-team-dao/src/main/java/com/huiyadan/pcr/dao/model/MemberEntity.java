package com.huiyadan.pcr.dao.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Table;

@Getter
@Setter
@Table(name = "member")
public class MemberEntity {
    /**
     * 第几期
     */
    @Column(name = "stage")
    private Integer stage;

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
     * QQ号
     */
    @Column(name = "qq")
    private String qq;
}