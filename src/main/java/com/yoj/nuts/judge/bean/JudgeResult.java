package com.yoj.nuts.judge.bean;

public enum JudgeResult {
    ACCEPTED("ACCEPTED"),
    PRESENTATION_ERROR("Presentation Error"),
    TIME_LIMIT_EXCEEDED("TimeLimit Exceeded"),
    MEMORY_LIMIT_EXCEEDED("Memory Limit Exceeded"),
    WRONG_ANSWER("Wrong Answer"),
    RUNTIME_ERROR("Runtime Error"),
    OUTPUT_LIMIT_EXCEEDED("Output Limit Exceeded"),
    COMPILE_ERROR("Compile Error"),
    SYSTEM_ERROR("System Error"),
    JUDGING("judging");
    private String name;

    JudgeResult(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return this.name;
    }
}
