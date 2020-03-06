package com.yoj.custom.properties.judge_bean;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@ToString
/**
* @Description:  window环境下才需要配置
* @Author: lmz
* @Date: 2019/10/20 
*/ 
public class Windows {
    //保存题目的路径，必须存在，path必须有‘/’结尾
    private String problemFilePath;
    //solution暂存路径
    private String solutionFilePath;

    private String ip;

    private String userName;

    private String password;
}
