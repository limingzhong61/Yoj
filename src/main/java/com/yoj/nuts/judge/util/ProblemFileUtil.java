package com.yoj.nuts.judge.util;

import com.alibaba.fastjson.JSON;
import com.yoj.web.bean.Problem;
import com.yoj.web.bean.util.JudgeData;
import org.apache.commons.io.FileUtils;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;
import java.util.List;

@Component
public interface ProblemFileUtil {
    /**
     * @Description: 创建problem判题的输入输出文件
     * @Param: [dirPath：创建文件的目录
     * ,problem]
     * @Author: lmz
     */
    default public void createFile(String dirPath, Problem problem) {
        File file = new File(dirPath);
        //先形成一个空目录
        if (file.exists()) {
            try {
                FileUtils.cleanDirectory(file);
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            file.mkdirs();
        }

        //创建子文件
        List<JudgeData> judgeData = JSON.parseArray(problem.getJudgeData(), JudgeData.class);
        for (int i = 0; i < judgeData.size(); i++) {
            try {
                FileUtils.write(new File(dirPath + "/" + i + ".in"), judgeData.get(i).getIn(), "utf-8");
                FileUtils.write(new File(dirPath + "/" + i + ".out"), judgeData.get(i).getOut(), "utf-8");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void createProblemFile(Problem problem);
}
