package com.huiyadan.pcr.dao.model;

import javax.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Table(name = "nicknames")
public class NicknamesEntity {
    @Id
    @Column(name = "id")
    private Integer id;

    @Column(name = "jp_name")
    private String jpName;

    @Column(name = "cn_name")
    private String cnName;

    @Column(name = "nicknames")
    private String nicknames;
}