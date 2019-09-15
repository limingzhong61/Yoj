package com.yoj.web.bean;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class Privilege {
    private Integer privilege_id;
    private Integer user_id;
    private String right;
}
