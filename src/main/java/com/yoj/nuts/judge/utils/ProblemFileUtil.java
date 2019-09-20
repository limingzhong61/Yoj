package com.yoj.nuts.judge.utils;

import com.alibaba.fastjson.JSON;
import com.yoj.web.bean.JudgeData;
import com.yoj.web.bean.Problem;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.util.List;

public class ProblemFileUtil {
    /**
    * @Description:  创建problem判题的输入输出文件
    * @Param: [dirPath：创建文件的目录
     * ,problem]
    * @return: boolean
    * @Author: lmz
    */
    private static void createFile(String dirPath, Problem problem) {
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
        for(int i = 0; i < judgeData.size(); i++){
            try {
                FileUtils.write(new File(dirPath+"/"+ i +".in"),judgeData.get(i).getIn(),"utf-8");
                FileUtils.write(new File(dirPath+"/"+ i +".out"),judgeData.get(i).getOut(),"utf-8");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static void createProblemFile(Problem problem){
        // window 环境
        //创建problemId的目录来保存文件
        String windowsPath = "E:/tmp/testData"+"/"+ problem.getProblemId();
        //必须保证liunx的前缀目录存在 "/tmp/testData/"
        String linuxPath = "/tmp/testData/" + problem.getProblemId();
        //linux环境
//        createFile(linuxPath,problem);

        //windows 环境
        createFile(windowsPath,problem);
        SSH2Util ssh2Util = new SSH2Util("106.54.94.80", "ubuntu", "nicolas!3125", 22);
        try {
                ssh2Util.putFile(windowsPath, "*", linuxPath);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

}
