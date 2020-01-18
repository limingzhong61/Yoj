package com.yoj.web.service;

import com.yoj.web.pojo.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class UserServiceTest {
    @Autowired
    private UserService userService;

    @Test
    public void updateScoreById() {
        for(User user : userService.getUserList(new User())){
            userService.updateScoreById(user.getUserId());
        }

    }
}