package com.yoj;

import com.yoj.custom.judge.util.ExecutorUtil;
import org.jasypt.encryption.StringEncryptor;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class YojApplicationTests {
    @Autowired
    private ExecutorUtil executorUtil;

    @Test
    public void test(){
        System.out.println(executorUtil);
    }

}
