package com.yoj.custom.judge.enums;

public enum JudgeResult {
    ACCEPTED("ACCEPTED"),                               //0
    PRESENTATION_ERROR("Presentation Error"),           //1
    TIME_LIMIT_EXCEEDED("TimeLimit Exceeded"),          //2
    MEMORY_LIMIT_EXCEEDED("Memory Limit Exceeded"),     //3
    WRONG_ANSWER("Wrong Answer"),                       //4
    RUNTIME_ERROR("Runtime Error"),                     //5
    OUTPUT_LIMIT_EXCEEDED("Output Limit Exceeded"),     //6
    COMPILE_ERROR("Compile Error"),                     //7
    SYSTEM_ERROR("system_error"),                       //8 ,前8个不能改顺序，要和judge.py一致
    JUDGING("judging"),                                 //9
    WAIT_REJUDGE("wait_rejudge");                       //10
    private String name;                                //*

    JudgeResult(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return this.name;
    }
}
