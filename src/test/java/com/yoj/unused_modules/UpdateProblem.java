package com.yoj.unused_modules;

import com.yoj.custom.judge.util.ProblemFileUtil;
import com.yoj.web.pojo.Problem;
import com.yoj.web.service.ProblemService;
import com.yoj.web.service.SolutionService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
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
    ProblemFileUtil problemFileUtil;

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
