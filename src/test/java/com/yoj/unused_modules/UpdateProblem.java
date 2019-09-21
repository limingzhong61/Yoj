package com.yoj.unused_modules;

import com.yoj.web.bean.Problem;
import com.yoj.web.service.ProblemService;
import com.yoj.web.service.SolutionService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class UpdateProblem {

    @Autowired
    SolutionService solutionService;

    @Autowired
    ProblemService problemService;

    /**
    * @Description:  更新问题 ？ 后期改联合，用在admin模块，或者定期改变；
    * @return: void
    * @Author: lmz
    */
    @Test
    public void updateProblemSubmissionByProblemId(){
        int pid = 1;
        int accepted = solutionService.countAcceptedByProblemId(pid);
        int submission = solutionService.countSubmissionByProblemId(pid);
        Problem problem = new Problem();
        problem.setProblemId(1);
        problem.setSubmissions(submission);
        problem.setAccepted(accepted);
        problemService.updateByPrimaryKeySelective(problem);
    }
}
