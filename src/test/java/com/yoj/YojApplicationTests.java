package com.yoj;

import com.yoj.nuts.judge.utils.ProblemFileUtil;
import com.yoj.used.MyInterface;
import com.yoj.web.bean.Problem;
import com.yoj.web.service.ProblemService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class YojApplicationTests {

    @Autowired
    ProblemService problemService;

    @Autowired
    @Qualifier("impl1")
//    @Resource(name = "impl2")
    MyInterface myInterface;

    @Test
    public void createProblemFile() throws Exception{
        String dirPath = "E:/tmp/testData";
        Problem problem = problemService.queryById(1);
        ProblemFileUtil.createProblemFile(problem);
    }

    @Test
    public void testInverseOfControl(){
        myInterface.className();
    }
}
