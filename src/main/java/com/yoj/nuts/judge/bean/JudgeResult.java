package com.yoj.nuts.judge.bean;

public enum JudgeResult {
    Accepted("Accepted"),
    PresentationError("Presentation Error"),
    TimeLimitExceeded("TimeLimit Exceeded"),
    MemoryLimitExceeded("Memory Limit Exceeded"),
    WrongAnswer("Wrong Answer"),
    RuntimeError("Runtime Error"),
    OutputLimitExceeded("Output Limit Exceeded"),
    CompileError("Compile Error"),
    SystemError("System Error");
    private String name;

    JudgeResult(String name){
        this.name = name;
    }

    @Override
    public String toString() {
        return this.name;
    }
}
