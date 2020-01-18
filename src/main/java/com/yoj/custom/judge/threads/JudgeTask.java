package com.yoj.custom.judge.threads;

import com.yoj.custom.judge.Judge;
import com.yoj.custom.judge.bean.JudgeSource;
import com.yoj.custom.judge.enums.JudgeResult;
import com.yoj.web.pojo.Solution;
import com.yoj.web.service.SolutionService;
import com.yoj.web.service.UserService;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Scope("prototype")//spring 多例
@Getter
@Setter
@NoArgsConstructor
@Slf4j
public class JudgeTask implements Runnable {
    @Autowired
    private Judge judge;
    @Autowired
    private SolutionService solutionService;
    @Autowired
    private UserService userService;

    private JudgeSource judgeSource;

    public JudgeTask(JudgeSource judgeSource) {
        this.judgeSource = judgeSource;
    }


    @Override
    public void run() {
        //业务操作
        System.out.println("多线程已经处理任务插入系统，Solution号：" + judgeSource.getSolutionId());
        Solution solution = judge.judge(judgeSource);
        if(solutionService.updateById(solution) == null){
            log.error("update error at judgeTask");
        }else{
            log.info("update successfully at judgeTask");
        }
        // correct solution to update user score.
        if(solution.getResult() == JudgeResult.ACCEPTED.ordinal()){
            userService.updateScoreById(solution.getUserId());
        }
    }
}