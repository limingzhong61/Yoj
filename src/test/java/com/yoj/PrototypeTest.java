package com.yoj;

import com.yoj.custom.judge.bean.JudgeSource;
import com.yoj.custom.judge.threads.JudgeThreadPoolManager;
import com.yoj.web.pojo.Solution;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class PrototypeTest {
    @Autowired
    public JudgeThreadPoolManager threadPoolManager;

    @Test
    public void test(){
        Solution solution = new Solution();
        solution.setUserId(1);
        JudgeSource judgeSource = new JudgeSource();
//        judgeSource.setSolution(solution);
//        solution.setUserName(userDetail.getUsername());

//        BeanUtils.copyProperties(solution,judgeSource);
//        judgeSource.setMemoryLimit(256);
//        judgeSource.setTimeLimit(1000);
//        judgeSource.setSolutionId(1);
//        Solution insertSolution = solutionService.insertSolution(solution);
//        if (insertSolution == null) {
//            return Msg.fail("insert solution fail");
//        }
//        judgeSource.setSolutionId(insertSolution.getSolutionId());
        threadPoolManager.addTask(judgeSource);
    }

}
