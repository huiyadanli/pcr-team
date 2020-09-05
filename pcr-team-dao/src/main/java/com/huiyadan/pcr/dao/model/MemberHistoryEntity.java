package com.huiyadan.pcr.dao.model;

import javax.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Table(name = "member_history")
public class MemberHistoryEntity {
    @Column(name = "stage")
    private Integer stage;

    @Column(name = "game_id")
    private String gameId;

    @Column(name = "game_nickname")
    private String gameNickname;

    @Column(name = "qq")
    private String qq;
}