package com.yoj.nuts.judge.util.impl;


import com.yoj.nuts.judge.Judge;
import com.yoj.nuts.judge.util.ExecutorUtil;
import com.yoj.nuts.properties.JudgeProperties;
import com.yoj.web.bean.Solution;
import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.File;

/**
* @Description: local environment
* @Param:
* @return:
* @Author: lmz
* @Date: 2019/10/27
*/
public class LocalJudge extends Judge{
    private String[] fileNames = {"main.c", "main.cpp", "Main.java", "main.py"};

    @Autowired
    private JudgeProperties judgeProperties;
    @Autowired
    private ExecutorUtil executor;


    @Override
    public void createSolutionFile(Solution solution, String linuxPath, String windowsPath) throws Exception {
        File file = new File(linuxPath);
        file.mkdirs();
        FileUtils.write(new File(linuxPath + "/" + fileNames[solution.getLanguage()]),
                solution.getCode(), "utf-8");
    }

    @Override
    public void deleteSolutionFile(String linuxPath, String windowsPath) {
        executor.execute("rm -rf " + linuxPath);
    }
}
