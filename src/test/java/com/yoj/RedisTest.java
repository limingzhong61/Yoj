package com.yoj;

import com.yoj.web.cache.UserCacheUtil;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class RedisTest {
    @Autowired
    private UserCacheUtil userCache;
    @Test
    public void test(){
        userCache.delById(27);
    }

}
