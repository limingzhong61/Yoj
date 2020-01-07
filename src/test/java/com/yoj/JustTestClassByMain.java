package com.yoj;

import com.yoj.custom.judge.bean.JudgeSource;
import com.yoj.web.pojo.Problem;
import com.yoj.web.pojo.Solution;
import org.springframework.beans.BeanUtils;

public class JustTestClassByMain {
    public static void main(String[] args) {
        JudgeSource judgeSource = new JudgeSource();
        Problem problem = new Problem();
        Solution solution = new Solution();
        solution.setSolutionId(1);
        problem.setProblemId(2);
        BeanUtils.copyProperties(solution,judgeSource );
        BeanUtils.copyProperties(problem,judgeSource );
        BeanUtils.copyProperties(solution,judgeSource );
        System.out.println(judgeSource);
    }
}
