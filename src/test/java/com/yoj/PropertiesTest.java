package com.yoj;

import com.yoj.nuts.properties.JudgeProperties;
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
    @Test
    public void test(){
        System.out.println(judgeProperties);
    }
}
