package com.yoj.judge.bean.static_fianl;

public class Results {
    public static final Integer Accepted = 0;
    public static final Integer PresentationError = 1;
    public static final Integer TimeLimitExceeded = 2;
    public static final Integer MemoryLimitExceeded = 3;
    public static final Integer WrongAnswer = 4;
    public static final Integer RuntimeError = 5;
    public static final Integer OutputLimitExceeded = 6;
    public static final Integer SystemError = 7;
    public static final Integer CompileError = 8;

    public static final String[] RESULT_STR =
            {
                    "Accepted",
                    "Presentation Error",
                    "Time Limit Exceeded",
                    "Memory Limit Exceeded",
                    "Wrong Answer",
                    "Runtime Error",
                    "Output Limit Exceeded",
                    "System Error",
                    "Compile Error"
            };

    public static String toString(Integer result) {
        return RESULT_STR[result];
    }
}
