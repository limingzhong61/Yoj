package com.yoj.nuts.judge.util.impl;


import com.yoj.nuts.judge.util.ExecutorUtil;
import com.yoj.nuts.properties.JudgeProperties;
import org.springframework.beans.factory.annotation.Autowired;

/**
* @Description: local environment
* @Param:
* @return:  
* @Author: lmz
* @Date: 2019/10/27 
*/ 
public class LocalJudge {
    private String[] fileNames = {"main.c", "main.cpp", "Main.java", "main.py"};

    @Autowired
    private JudgeProperties judgeProperties;
    @Autowired
    private ExecutorUtil executor;

}
