package com.yoj.web.bean;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@ToString
public class ContestProblem {
    private Integer problemId;
    private Integer contestId;
    private Integer cAccepted;
    private Integer cSubmit;
}
