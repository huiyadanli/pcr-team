package com.huiyadan.pcr.api.bigfun.team.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * @author huiyadanli
 */
@Setter
@Getter
@JsonIgnoreProperties(ignoreUnknown = true)
public class ClanData {

    /**
     *
     * 有一个名为 clan 的对象
     * {"clan":{"name":"王母山女装俱乐部","last_ranking":11764,"last_total_ranking":"AA","all_ranking":[{"clan_battle_name":"天秤座","month":"10","ranking":11764},{"clan_battle_name":"处女座","month":"9","ranking":3468},{"clan_battle_name":"-","month":"8","ranking":3963},{"clan_battle_name":"-","month":"7","ranking":4717},{"clan_battle_name":"-","month":"6","ranking":5881},{"clan_battle_name":"-","month":"6","ranking":9917},{"clan_battle_name":"-","month":"5","ranking":10851}]}
     *
     */


    private List<ClanViewer> data;
}

