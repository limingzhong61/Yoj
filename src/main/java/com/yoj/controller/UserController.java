package com.yoj.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.yoj.model.entity.User;
import com.yoj.model.pojo.util.UserDetailsImpl;
import com.yoj.model.vo.Msg;
import com.yoj.service.SolutionService;
import com.yoj.service.UserService;
import com.yoj.storage.StorageService;
import com.yoj.utils.auth.UserUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    UserService userService;
    @Autowired
    SolutionService solutionService;
    @Autowired
    private UserUtil currentUserUtil;
    @Autowired
    private StorageService storageService;

    @GetMapping("/currentInfo")
    @PreAuthorize("isAuthenticated()")//    need login
    public Msg getCurrentUserInfo() {
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
    public Msg getUserSet(@PathVariable("pageNumber") Integer pageNumber, User user) {
        PageHelper.startPage(pageNumber, 10);
        List<User> users = userService.getUserList(user);
        PageInfo<User> page = new PageInfo<User>(users, 5);
        return Msg.success().add("pageInfo", page);
    }

    @PutMapping("/update")
    @PreAuthorize("isAuthenticated()") //  need login
    public Msg updateUserInfo(@RequestBody User user) {
        int currentUserId = currentUserUtil.getUserDetail().getUserId();
        if(user.getUserId() != currentUserId || currentUserUtil.isAdmin()){
            return Msg.fail();
        }
        User updateUser = userService.updateUserInfoById(user);
        if (updateUser == null) {
            return Msg.fail();
        }
        return Msg.success();
    }

    @PostMapping("/update/avatar")
    @PreAuthorize("isAuthenticated()")//    need login
    public Msg updateUserAvatar(MultipartFile uploadFile, HttpServletRequest req) {

//        String originalFilename = uploadFile.getOriginalFilename();
        boolean successful = storageService.storeAvatar(uploadFile, req);
        if (!successful) {
            return Msg.fail();
        }
        return Msg.success();
    }

    @GetMapping("/judgePassword")
    @PreAuthorize("isAuthenticated()")//    need login
    public Msg JudgePassword(User user) {
        if (!userService.judgePasswordByUserId(user.getUserId(), user.getPassword())) {
            return Msg.fail();
        }
        return Msg.success();
    }
    @GetMapping("/updateMyPassword")
    @PreAuthorize("isAuthenticated()")//    need login
    public Msg updateMyPassword(String oldPassword,String newPassword) {
        if (!userService.updateMyPassword(currentUserUtil.getUserDetail().getUserId(),oldPassword,newPassword)) {
            return Msg.fail();
        }
        return Msg.success();
    }
}
