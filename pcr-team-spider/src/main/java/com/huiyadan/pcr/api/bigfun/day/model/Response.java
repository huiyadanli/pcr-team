package com.huiyadan.pcr.api.bigfun.day.model;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * @author huiyadanli
 */
@Setter
@Getter
public class Response {

    private String code;

    private List<Member> data;
}
