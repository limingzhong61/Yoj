package com.yoj.model.entity;

import com.yoj.model.dto.JudgeCase;
import lombok.Data;

import java.util.List;

@Data
public class Problem {
    private Integer problemId;
    //作者
    private Integer userId;

    private String title;

    private String tag;

    private String description;
    //输入格式
    private String formatInput;
    //输出格式
    private String formatOutput;
    //输入样例
    private String sampleInput;
    //输出样例
    private String sampleOutput;
    //提示
    private String hint;

    //内存限制mb
    private Integer memoryLimit;
    //时间限制ms
    private Integer timeLimit;

    //-------------------非表格字段--------------------
    private Integer accepted;

    private Integer submissions;

    private Integer userSolved;

    private Integer userSubmitted;
    // score of contest problem
    private Integer score;
    // after parse judgeData
    private List<JudgeCase> judgeData;
    //当前用户是否提交、解决、
//    private Integer state;
}
