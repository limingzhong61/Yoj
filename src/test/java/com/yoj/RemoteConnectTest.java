package com.yoj;

import com.yoj.nuts.judge.util.PropertiesUtil;
import com.yoj.nuts.judge.util.SSH2Util;
import com.yoj.nuts.judge.util.impl.RemoteExecutor;
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
    @Test
    public void  testFileSSHConnection(){
        SSH2Util ssh2Util = new SSH2Util(PropertiesUtil.get("ip"), PropertiesUtil.get("userName"), PropertiesUtil.get("password"), 22);
    }

}
