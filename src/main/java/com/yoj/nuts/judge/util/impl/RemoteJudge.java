package com.yoj.nuts.judge.util.impl;

import com.yoj.nuts.judge.Judge;
import com.yoj.nuts.judge.util.ExecutorUtil;
import com.yoj.nuts.judge.util.SSH2Util;
import com.yoj.nuts.properties.JudgeProperties;
import com.yoj.web.bean.Solution;
import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;

/**
 * @Description: remote environment, windows 环境,
 * @Param:
 * @return:
 * @Author: lmz
 * @Date: 2019/10/27
 */
@Component
public class RemoteJudge extends Judge {

    private String[] fileNames = {"main.c", "main.cpp", "Main.java", "main.py"};

    @Autowired
    private JudgeProperties judgeProperties;

    @Autowired
    private ExecutorUtil executor;

    public void createSolutionFile(Solution solution, String linuxPath, String windowsPath) throws Exception {
        // windows 环境
        File file = new File(windowsPath);
        file.mkdirs();

        FileUtils.write(new File(windowsPath + "/" + fileNames[solution.getLanguage()]),
                solution.getCode(), "utf-8");
        SSH2Util ssh2Util = new SSH2Util(judgeProperties.getIp(), judgeProperties.getUserName(), judgeProperties.getPassword(), 22);
        ssh2Util.putFile(windowsPath, fileNames[solution.getLanguage()], linuxPath);

    }

    public void deleteSolutionFile(String linuxPath, String windowsPath) {
        executor.execute("rm -rf " + linuxPath);
        try {
            FileUtils.deleteDirectory(new File(windowsPath));
        } catch (IOException ee) {
            ee.printStackTrace();
        }
    }
}
