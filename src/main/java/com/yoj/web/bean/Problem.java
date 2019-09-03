package com.yoj.web.bean;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Problem {
    private Integer problemId;

    private Integer userId;

    private String title;

    private String description;

    private Integer memoryLimit;

    private Integer timeLimit;

    private Integer accepted;

    private Integer submissions;

    private Integer state;
    
    // 通过的百分率
    private Integer acRate;
    
}
