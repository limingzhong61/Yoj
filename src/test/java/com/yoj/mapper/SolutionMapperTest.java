package com.yoj.mapper;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
@RunWith(SpringRunner.class)
@SpringBootTest
public class SolutionMapperTest {
    @Autowired
    private SolutionMapper solutionMapper;

    @Test
    public void getByContestId() {
//        List<Solution> solutions = solutionMapper.getByContestId(new Integer(191));
//        System.out.println(solutions);
    }

}