package com.yoj;

import com.yoj.custom.judge.util.ProblemFileUtil;
import com.yoj.custom.judge.util.impl.LocalProblemFileUtil;
import com.yoj.custom.judge.util.impl.RemoteProblemFileUtil;
import com.yoj.custom.properties.JudgeProperties;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class PropertiesTest {
    @Autowired
    JudgeProperties judgeProperties;

    @Autowired
    ProblemFileUtil problemFileUtil;
    @Test
    public void testLocal(){
        System.out.println(problemFileUtil instanceof LocalProblemFileUtil);
//        problemFileUtil.createProblemFile(new Problem());
    }

    @Test
    public void testRemote(){
        System.out.println(problemFileUtil instanceof RemoteProblemFileUtil);
//        problemFileUtil.createProblemFile(new Problem());
    }
}
