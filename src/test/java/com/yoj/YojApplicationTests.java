package com.yoj;

import com.yoj.used.MyInterface;
import com.yoj.web.service.ProblemService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.StringRedisTemplate;
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

    @Autowired
    StringRedisTemplate stringRedisTemplate;

    @Test
    public void createProblemFile() throws Exception{
//        String dirPath = "E:/tmp/testResult";
//        Problem problem = problemService.getById(1);
//        ProblemFileUtil.createProblemFile(problem);
    }

    @Test
    public void testInverseOfControl(){
        myInterface.className();
    }


    @Test
    public void template(){
//        for(int i = 0; i < 3; i++){
//            String checkCode = String.valueOf(new Random().nextInt(899999) + 100000);
//            //邮件设置
//            String email = "email"+checkCode;
//            stringRedisTemplate.opsForValue().append(email,checkCode);
//        }
//        stringRedisTemplate.opsForList("email",)
    }
}
