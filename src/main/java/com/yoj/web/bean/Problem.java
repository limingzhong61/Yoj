package com.yoj.web.bean;

import com.alibaba.fastjson.JSONArray;
import lombok.*;

import java.util.List;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class Problem{
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

    private Integer accepted;

    private Integer submissions;

    private String judgeData;

    //非表格字段
    // 通过的百分率
    private Integer acRate;
    //当前用户是否解决
    private Boolean solved;

    private Integer state;

    private List<JudgeData> data;
    public Problem parseJudgeData() {
        data = JSONArray.parseArray(judgeData,JudgeData.class);
        return this;
    }
}
