package com.yoj.custom.judge.util;

import com.alibaba.fastjson.JSON;
import com.yoj.web.pojo.Problem;
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
    default void createFile(String dirPath, Problem problem) {
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
        List<String> judgeData = JSON.parseArray(problem.getJudgeData(), String.class);
        for (int judgeNumber = 0; judgeNumber < judgeData.size(); judgeNumber++) {
            List<String> judgeCase = JSON.parseArray(judgeData.get(judgeNumber), String.class);
            String[] nameInfo = {".in",".out"};
            for (int j = 0; j < judgeCase.size(); j++) {
                try {
                    FileUtils.write(new File(dirPath + "/" + judgeNumber + nameInfo[j]), judgeCase.get(j), "utf-8");
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

        }
    }

    void createProblemFile(Problem problem);
}
