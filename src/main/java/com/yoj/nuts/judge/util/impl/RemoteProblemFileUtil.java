package com.yoj.nuts.judge.util.impl;

import com.yoj.nuts.judge.util.ProblemFileUtil;
import com.yoj.nuts.judge.util.SSH2Util;
import com.yoj.nuts.properties.JudgeProperties;
import com.yoj.web.bean.Problem;
import org.springframework.beans.factory.annotation.Autowired;

/**
* @Description:   windows 环境,创建problemId的目录来保存文件
* @Author: lmz
*/
public class RemoteProblemFileUtil implements ProblemFileUtil {
    @Autowired
    JudgeProperties judgeProperties;

    @Override
    public void createProblemFile(Problem problem) {
        //必须保证linux的前缀目录存在 "/tmp/testResult/"
        String linuxPath = judgeProperties.getLinux().getProblemFilePath() + problem.getProblemId();
        //创建problemId的目录来保存文件
        String windowsPath = judgeProperties.getWindows().getProblemFilePath() + problem.getProblemId();
        createFile(windowsPath, problem);
        SSH2Util ssh2Util = new SSH2Util(judgeProperties.getIp(), judgeProperties.getUserName(), judgeProperties.getPassword(), 22);
        try {
            ssh2Util.putFile(windowsPath, "*", linuxPath);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
