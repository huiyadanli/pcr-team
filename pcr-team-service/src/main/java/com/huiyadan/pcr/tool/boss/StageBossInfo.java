package com.huiyadan.pcr.tool.boss;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * 当期 boss 信息
 * @author huiyadanli
 */
@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class StageBossInfo {

    /**
     * 第几期
     */
    private Integer stage;

    /**
     * boss 名称列表，按顺序
     */
    private List<String> bossNames;

    /**
     * boss 血量列表，按顺序，可以为空
     */
    private List<Integer> bossHps;
}
