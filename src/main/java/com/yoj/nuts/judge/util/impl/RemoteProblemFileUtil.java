package com.yoj.nuts.judge.util.impl;

import com.yoj.nuts.judge.util.ProblemFileUtil;
import com.yoj.nuts.judge.util.PropertiesUtil;
import com.yoj.nuts.judge.util.SSH2Util;
import com.yoj.web.bean.Problem;
import org.springframework.stereotype.Service;

/**
* @Description:   window 环境,创建problemId的目录来保存文件
* @Author: lmz
*/
@Service
public class RemoteProblemFileUtil implements ProblemFileUtil {
    @Override
    public void createProblemFile(Problem problem) {
        //必须保证linux的前缀目录存在 "/tmp/testData/"
        String linuxPath = PropertiesUtil.get("linux.problemFilePath") + problem.getProblemId();
        //创建problemId的目录来保存文件
        String windowsPath = PropertiesUtil.get("windows.problemFilePath") + problem.getProblemId();
        createFile(windowsPath, problem);
        SSH2Util ssh2Util = new SSH2Util(PropertiesUtil.get("ip"), PropertiesUtil.get("userName"), PropertiesUtil.get("password"), 22);
        try {
            ssh2Util.putFile(windowsPath, "*", linuxPath);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
