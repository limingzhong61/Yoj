package com.yoj.nuts.judge.util.impl;

import com.yoj.nuts.judge.util.ProblemFileUtil;
import com.yoj.nuts.judge.util.PropertiesUtil;
import com.yoj.web.bean.Problem;
import org.springframework.stereotype.Service;

//linux环境
@Service()
public class LocalProblemFileUtil implements ProblemFileUtil {
    @Override
    public void createProblemFile(Problem problem) {
        //        //必须保证linux的前缀目录存在 "/tmp/testData/"
        String linuxPath = PropertiesUtil.get("linux.problemFilePath") + problem.getProblemId();
        createFile(linuxPath, problem);
    }
}
