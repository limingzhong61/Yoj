package com.yoj;

import com.yoj.web.bean.Solution;
import com.yoj.web.dao.ProblemMapper;
import com.yoj.web.dao.SolutionMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class MybatisMapperTest {
    @Autowired
    ProblemMapper problemMapper;

    @Autowired
    SolutionMapper solutionMapper;

//    @Test
//    public void testSelectInsert(){
//        Problem problem = problemMapper.queryById(1);
//        System.out.println(problemMapper.insertSelective(problem));
//        System.out.println("problemId:"+problem.getProblemId());
//    }
//
//    @Test
//    public void testProblemInsert(){
//        Problem problem = problemMapper.queryById(1);
//        System.out.println(problemMapper.insert(problem));
//        System.out.println("problemId:"+problem.getProblemId());
//    }

    @Test
    public void testSelect(){
//        Problem problem = problemMapper.queryProblemTitleAndIdById(1);
//        System.out.println(problem);
        List<Solution> allByDesc = solutionMapper.getAllWithUserAndProblemName();
        System.out.println(allByDesc);
    }
}
