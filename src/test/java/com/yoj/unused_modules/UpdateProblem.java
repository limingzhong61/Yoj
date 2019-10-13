package com.yoj.unused_modules;

import com.yoj.nuts.judge.util.ProblemFileUtil;
import com.yoj.web.bean.Problem;
import com.yoj.web.service.ProblemService;
import com.yoj.web.service.SolutionService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class UpdateProblem {

    @Autowired
    SolutionService solutionService;

    @Autowired
    ProblemService problemService;

    @Autowired
    @Qualifier("localProblemFileUtil")
    ProblemFileUtil problemFileUtil;

    /**
     * @Description: 更新问题的submissions和accepted ？ 后期改联合，用在admin模块，或者定期改变；
     * @return: void
     * @Author: lmz
     */
    @Test
    public void updateProblemSubmissionByProblemId() {
        int pid = 1;
        int accepted = solutionService.countAcceptedByProblemId(pid);
        int submission = solutionService.countSubmissionByProblemId(pid);
        Problem problem = new Problem();
        problem.setProblemId(1);
        problem.setSubmissions(submission);
        problem.setAccepted(accepted);
        problemService.updateByPrimaryKeySelective(problem);
    }

    /**
     * @Description: update problem flies 更新问题模块，便于系统移植
     * @Param: []
     * @return: void
     * @Author: lmz
     * @Date: 2019/10/11
     */
    @Test
    public void updateProblemFiles() throws Exception {
        List<Problem> all = problemService.getAll();
        int cnt = 0;
        try{
            for(Problem problem : all){
                problemFileUtil.createProblemFile(problem);
                cnt++;
                System.out.println(cnt);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        if(cnt != all.size()){
            throw new Exception("更新失败");
        }
    }
}
