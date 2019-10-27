package com.yoj.web.controller.user;

import com.yoj.web.bean.User;
import com.yoj.web.bean.util.Msg;
import com.yoj.web.service.UserService;
import com.yoj.web.util.EmailSender;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.*;

/**
 * @Description: 注册控制层
 * @Author: lmz
 * @Date: 2019/9/27
 */
@RestController
@RequestMapping("/user/r")
public class RegisterController {
    @Autowired
    UserService userService;
    @Autowired
    EmailSender emailSender;

    @Autowired
    StringRedisTemplate stringRedisTemplate;

    @PostMapping("/register")
    public Msg register(@RequestBody User user) {

        String checkCode = emailSender.getEmailCheckCode(user.getEmail());
        Msg msg = new Msg();
        msg.setSuccess(true);
        if (checkCode == null || !checkCode.equals(user.getEmailCheckCode())) {
            msg.setSuccess(false);
            msg.add("emailCheckJudge", "邮箱验证码错误");
        }
        if (userService.getUserByName(user.getUserName()) != null) {
            msg.setSuccess(false);
            msg.add("userNameJudge", "用户名已存在");
        }
        if (!msg.isSuccess()) {
            return msg;
        }
        if (userService.insertUserUseCache(user) == null) {
            return Msg.fail("系统错误");
        }
        emailSender.delEmailCheckCode(user.getEmail());
        return Msg.success();
    }

    @GetMapping("/validateUserName/{userName}")
    public Msg validateUserName(@PathVariable("userName") String userName) {
        if (userService.getUserByName(userName) != null) {
            return Msg.fail("用户名已存在");
        }
        return Msg.success();
    }

    @GetMapping("/validateEmail/{email}")
    public Msg validateEmail(@PathVariable("email") String email) {
        if (userService.queryExistByEmail(email)) {
            return Msg.fail("邮箱已被注册");
        }
        return Msg.success();
    }

    @GetMapping("/getEmailCheckCode/{email}")
    public Msg getCheckCode(@PathVariable("email") String email) {
        if (userService.queryExistByEmail(email)) {
            return Msg.fail("邮箱已被注册");
        }
        String checkCode = emailSender.sendRegisterEmail(email);
        if (checkCode == null) {
            return Msg.fail("发送邮件失败，请检查邮箱地址是否正确");
        }
        return Msg.success();
    }
}
