package com.yoj.custom.judge.util.impl;

import com.yoj.custom.judge.bean.JudgeCase;
import com.yoj.custom.judge.util.ProblemFileUtil;
import com.yoj.custom.properties.JudgeProperties;
import com.yoj.web.pojo.Problem;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

//linux环境,local环境
//@ConditionalOnBean()
public class LocalProblemFileUtil implements ProblemFileUtil {

    @Autowired
    private JudgeProperties judgeProperties;

    @Override
    public boolean createProblemFile(Problem problem) {
        return createFile(this.getLinuxPath(problem.getProblemId()), problem);
    }

    @Override
    public boolean updateProblemFile(int problemId, List<JudgeCase> judgeData) {
        return this.updateFileCommons(this.getLinuxPath(problemId), judgeData);
    }

    @Override
    public String getProblemDirPath(int problemId) {
        return judgeProperties.getLinux().getProblemFilePath() + "\\" + problemId;
    }

    /**
     * 根据pid返回相应的文件夹
     * 必须保证linux的前缀目录存在 "/tmp/testResult/"
     *
     * @param problemId
     * @return
     */
    private String getLinuxPath(Integer problemId) {
        return judgeProperties.getWindows().getProblemFilePath() + "\\" + problemId;
    }

    @Override
    public List<JudgeCase> getJudgeData(Integer problemId) {
        return this.getJudgeDataCommons(this.getLinuxPath(problemId));
    }
}
