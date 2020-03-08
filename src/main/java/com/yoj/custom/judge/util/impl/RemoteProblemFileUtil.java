package com.yoj.custom.judge.util.impl;

import com.yoj.custom.judge.bean.JudgeCase;
import com.yoj.custom.judge.util.ProblemFileUtil;
import com.yoj.custom.judge.util.SSH2Util;
import com.yoj.custom.properties.JudgeProperties;
import com.yoj.web.pojo.Problem;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * @Description: windows 环境,创建problemId的目录来保存文件
 * @Author: lmz
 */
public class RemoteProblemFileUtil implements ProblemFileUtil {
    @Autowired
    private JudgeProperties judgeProperties;
    @Autowired
    private SSH2Util ssh2Util;

    @Override
    public boolean createProblemFile(Problem problem) {
        String linuxPath = this.getLinuxPath(problem.getProblemId());
        String windowsPath = this.getWindowsPath(problem.getProblemId());
        if (!createFile(windowsPath, problem)) {
            return false;
        }
        if (!ssh2Util.putFile(windowsPath, "*", linuxPath)) {
            return false;
        }
        return true;
    }

    @Override
    public boolean updateProblemFile(int problemId, List<JudgeCase> judgeData) {
        if (!this.updateFileCommons(getWindowsPath(problemId), judgeData)) {
            return false;
        }
        return true;
    }

    @Override
    public String getProblemDirPath(int problemId) {
        return judgeProperties.getWindows().getProblemFilePath() + "\\" + problemId;
    }

    /**
     * 根据pid返回相应的文件夹
     *
     * @param problemId
     * @return
     */
    private String getWindowsPath(Integer problemId) {
        return judgeProperties.getWindows().getProblemFilePath() + "\\" + problemId;
    }

    /**
     * 根据pid返回相应的文件夹
     * 必须保证linux的前缀目录存在 "/tmp/testResult"
     *
     * @param problemId
     * @return
     */
    private String getLinuxPath(Integer problemId) {
        return judgeProperties.getLinux().getProblemFilePath() + "\\" + problemId;
    }


    @Override
    public List<JudgeCase> getJudgeData(Integer problemId) {
        return this.getJudgeDataCommons(this.getWindowsPath(problemId));
    }
}
