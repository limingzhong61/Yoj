package com.yoj.custom.judge.util.impl;


import com.yoj.custom.judge.Judge;
import com.yoj.custom.judge.util.ExecutorUtil;
import com.yoj.custom.properties.JudgeProperties;
import com.yoj.web.pojo.Solution;
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

    @Autowired
    private JudgeProperties judgeProperties;
    @Autowired
    private ExecutorUtil executor;


    @Override
    public void createSolutionFile(Solution solution, String linuxPath, String windowsPath) throws Exception {
        File file = new File(linuxPath);
        file.mkdirs();
        FileUtils.write(new File(linuxPath + "/" + this.fileNames[solution.getLanguage()]),
                solution.getCode(), "utf-8");
    }

    @Override
    public void deleteSolutionFile(String linuxPath, String windowsPath) {
        executor.execute("rm -rf " + linuxPath);
    }
}
