package com.yoj.web.bean;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Problem {
    private Integer problemId;
    //作者
    private Integer userId;


    //内存限制mb
    private Integer memoryLimit;
    //时间限制ms
    private Integer timeLimit;

    private Integer accepted;

    private Integer submissions;

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

    // 通过的百分率
    private Integer acRate;
    //当前用户是否解决
    private Boolean solved;

    private Integer state;
}
