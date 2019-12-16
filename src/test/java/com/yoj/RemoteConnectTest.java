package com.yoj;

import com.yoj.custom.judge.util.impl.RemoteExecutor;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class RemoteConnectTest {

    @Test
    public void  testExecutor(){
        System.out.println(new RemoteExecutor());
    }

}
