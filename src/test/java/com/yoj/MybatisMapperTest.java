package com.yoj;

import com.alibaba.fastjson.JSONArray;
import com.yoj.web.bean.JudgeData;
import com.yoj.web.bean.Problem;
import com.yoj.web.dao.ProblemMapper;
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

    @Test
    public void testSelectInsert(){
        Problem problem = problemMapper.queryById(1);
        System.out.println(problemMapper.insertSelective(problem));
        System.out.println("problemId:"+problem.getProblemId());
    }
//
    @Test
    public void testProblemInsert(){
        Problem problem = problemMapper.queryById(1);
        System.out.println(problemMapper.insert(problem));
        System.out.println("problemId:"+problem.getProblemId());
    }

    @Test
    public void testJSon(){
        Problem problem = problemMapper.queryById(1);
        String judgeData = problem.getJudgeData();
//        System.out.println(judgeData);
        List<JudgeData> parse = JSONArray.parseArray(judgeData,JudgeData.class);
        System.out.println(parse.get(1));
        System.out.println();
    }
}
