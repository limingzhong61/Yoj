package com.yoj;

import com.yoj.nuts.judge.bean.JudgeResult;

public class JustClass {
    public static void main(String[] args) {
        System.out.println(JudgeResult.Accepted.ordinal() + ""+ JudgeResult.SystemError);
    }
}
