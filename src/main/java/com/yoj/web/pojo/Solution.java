package com.yoj.web.pojo;

import lombok.Data;

import java.util.Date;

@Data
public class Solution {
    private Integer solutionId;

    private Integer problemId;

    private Integer userId;

    private String userName;
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
//    private String languageStr;
//    private String resultStr;
//    private User user;
//    private Problem problem;
}
