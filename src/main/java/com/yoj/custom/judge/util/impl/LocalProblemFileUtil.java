package com.yoj.custom.judge.util.impl;

import com.yoj.custom.judge.util.ProblemFileUtil;
import com.yoj.custom.properties.JudgeProperties;
import com.yoj.web.pojo.Problem;
import org.springframework.beans.factory.annotation.Autowired;

//linux环境,local环境
//@ConditionalOnBean()
public class LocalProblemFileUtil implements ProblemFileUtil {

    @Autowired
    private JudgeProperties judgeProperties;
    @Override
    public void createProblemFile(Problem problem) {
        //        //必须保证linux的前缀目录存在 "/tmp/testResult/"
        String linuxPath = judgeProperties.getLinux().getProblemFilePath() + problem.getProblemId();
        createFile(linuxPath, problem);
    }
}
