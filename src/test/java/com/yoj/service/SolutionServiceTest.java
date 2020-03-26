package com.yoj.service;

import com.yoj.model.entity.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class SolutionServiceTest {

    @Autowired
    private SolutionService solutionService;

    @Test
    public void getContestRankByContestId() {
        List<User> users = solutionService.getContestRankByContestId(19);
        for (User user : users) {
            System.out.println(user);
        }
    }
}