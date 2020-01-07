package com.yoj.web.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.yoj.web.pojo.User;
import com.yoj.web.pojo.util.Msg;
import com.yoj.web.pojo.util.UserDetailsImpl;
import com.yoj.web.service.SolutionService;
import com.yoj.web.service.UserService;
import com.yoj.web.util.auth.CurrentUserUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    UserService userService;
    @Autowired
    SolutionService solutionService;
    @Autowired
    private CurrentUserUtil currentUserUtil;

    @GetMapping("/currentInfo")
    public Msg getCurrentUserInfo() {
//        int i = 1 / 0; //test exception
        UserDetailsImpl userDetails = currentUserUtil.getUserDetail();
        if (userDetails == null) {
            return Msg.fail();
        }
        User user = userService.getUserById(userDetails.getUserId());
        return Msg.success().add("user", user);
    }

    @GetMapping("info/{userId}")
    public Msg getUserInfo(@PathVariable("userId") Integer userId) {
        User user = userService.getUseById(userId);
        if (user == null) {
            return Msg.fail();
        }
        user.setAccepted(solutionService.countAcceptedByUserId(userId));
        user.setSubmissions(solutionService.countSubmissionsByUserId(userId));
        user.setAttempted(solutionService.countAttemptedByUserId(userId));
        user.setSolved(solutionService.countSolvedByUserId(userId));
        return Msg.success().add("user", user);
    }

    @GetMapping("/set/{pageNumber}")
    public Msg getUserInfo(@PathVariable("pageNumber") Integer pageNumber, User user) {
        PageHelper.startPage(pageNumber, 10);
        List<User> users = userService.getUserList(user);
        PageInfo<User> page = new PageInfo<User>(users, 5);
        return Msg.success().add("pageInfo", page);
    }

    @PutMapping("/update")
    //    need login
    @PreAuthorize("isAuthenticated()")
    public Msg updateUserInfo(@RequestBody User user) {
        // update current user info
        user.setUserId(currentUserUtil.getUserDetail().getUserId());
        User updateUser = userService.updateUserInfoById(user);
        if (updateUser == null) {
            return Msg.fail();
        }
        return Msg.success();
    }
}
