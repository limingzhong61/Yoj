package com.yoj.model.entity;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

//@Builder
@Data
@NoArgsConstructor
public class Solution {
    private Integer solutionId;

    private Integer problemId;
    // have a contest id when contest
    private Integer contestId;

    private Integer userId;

    //	Language
    private Integer language;

    private String code;
    //	JudgeResult
    private Integer result;
    //ms
    private Integer runtime;
    //memoryInfo = this.memory / 10 + "KB"
    private Integer memory;

    private String errorMessage;

    private Date submitTime;

    private String testResult;

    private Byte share;

    //非表格字段
    // a score of contest problem
    private Integer score;
    private String nickName;
//    private User user;
//    private Problem problem;
}
