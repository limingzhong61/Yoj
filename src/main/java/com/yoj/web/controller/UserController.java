package com.yoj.web.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.yoj.web.util.auth.CurrentUserUtil;
import com.yoj.web.pojo.User;
import com.yoj.web.pojo.util.Msg;
import com.yoj.web.service.SolutionService;
import com.yoj.web.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    UserService userService;
    @Autowired
    SolutionService solutionService;
    @Autowired
    private CurrentUserUtil userUtils;

    @GetMapping("/currentInfo")
    public Msg getCurrentUserInfo(){
        User user = userUtils.getUser();
        if(user == null){
            return Msg.fail();
        }
        user.setPassword("");
       return  Msg.success().add("user",user);
    }

    @GetMapping("info/{userId}")
    public Msg getUserInfo(@PathVariable("userId")Integer userId){
        User user = userService.getUseById(userId);
        if(user == null){
            return Msg.fail();
        }
        user.setPassword("");
        user.setAccepted(solutionService.countAcceptedByUserId(userId));
        user.setSubmissions(solutionService.countSubmissionsByUserId(userId));
        return  Msg.success().add("user",user);
    }

    @GetMapping("/set/{pageNumber}")
    public Msg getUserInfo(@PathVariable("pageNumber") Integer pageNumber,User user){
        PageHelper.startPage(pageNumber, 10);
        List<User> users = userService.getUserList(user);
        PageInfo<User> page = new PageInfo<User>(users, 5);
        return Msg.success().add("pageInfo", page);
    }
}
