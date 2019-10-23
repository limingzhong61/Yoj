package com.yoj.nuts.properties.judge_bean;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@ToString
public class Linux {
    //保存题目的路径，必须存在，path必须有‘/’结尾,不能保存在/tmp目录下，重启会被删除文件
    private String problemFilePath;
    //solution暂存路径
    private String solutionFilePath;
}
