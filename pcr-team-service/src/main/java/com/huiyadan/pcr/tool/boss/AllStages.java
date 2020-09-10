package com.huiyadan.pcr.tool.boss;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

/**
 * json 解析对象
 * @author huiyadanli
 */
@Getter
@Setter
public class AllStages {

    List<StageBossInfo> stages = new ArrayList<>();
}
