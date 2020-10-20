package com.huiyadan.pcr.api.bigfun.team.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;

/**
 * @author huiyadanli
 */
@Setter
@Getter
@JsonIgnoreProperties(ignoreUnknown = true)
public class ClanViewer {

    /**
     * uid
     */
    private String viewer_id;

    /**
     * 游戏昵称
     */
    private String username;

    /**
     * 更多：
     *
     * {
     *     "viewer_id":xxxx,
     *     "username":"xxxx",
     *     "number":2,
     *     "damage":1848061,
     *     "rate":"18.00%",
     *     "score":1848061
     * }
     */
}
