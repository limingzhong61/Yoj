package com.yoj;

import com.yoj.web.service.UserService;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.concurrent.Future;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ServiceTest {
    @Autowired
    private UserService userService;

    @Test
    public void updateAllUserProblemState() throws Exception {
        Future<Boolean> booleanFuture = userService.updateProblemState(1);
        while(!(booleanFuture.isDone())){
            Assert.assertTrue("fail",booleanFuture.get());
        }
    }

}
