package com.yoj.utils.judge;

import com.yoj.model.dto.JudgeCase;
import com.yoj.model.entity.Problem;
import com.yoj.model.properties.JudgeProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

//linux环境,local环境
//@ConditionalOnBean()
@Component
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
