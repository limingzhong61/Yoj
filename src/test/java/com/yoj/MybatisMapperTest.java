package com.yoj;

import com.yoj.web.bean.User;
import com.yoj.web.dao.ProblemMapper;
import com.yoj.web.dao.SolutionMapper;
import com.yoj.web.dao.UserMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class MybatisMapperTest {
    @Autowired
    ProblemMapper problemMapper;
    @Autowired
    SolutionMapper solutionMapper;
    @Autowired
    UserMapper userMapper;
    @Test
    public void testSelect(){

        List<User> userList = userMapper.getUserList(new User());
        System.out.println(userList);
    }
}
