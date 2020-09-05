package com.huiyadan.pcr.tool.boss;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * @author lihh
 */
@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class StageBossInfo {

    private Integer stage;

    private List<String> bossNames;

    private List<Integer> bossHps;
}
