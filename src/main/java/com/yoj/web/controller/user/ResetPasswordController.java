package com.yoj.web.controller.user;

import com.yoj.web.bean.User;
import com.yoj.web.bean.util.Msg;
import com.yoj.web.service.UserService;
import com.yoj.web.util.EmailSender;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @Description: 重置密码控制层
 * @Author: lmz
 * @Date: 2019/9/27
 */
@RestController
@RequestMapping("/user/reset")
public class ResetPasswordController {

    @Autowired
    UserService userService;

    @Autowired
    EmailSender emailSender;

    @GetMapping("/emailCode/{email}")
    public Msg getEmailCode(@PathVariable("email") String email) {
        User user = userService.getUserByEmail(email);
        if (user == null) {
            return Msg.fail("该邮箱没有被注册");
        }
        String checkCode = emailSender.sendResetPasswordEmail(email);
        if(checkCode == null){
            return Msg.fail("发送邮件失败，请稍后重试");
        }
        emailSender.delEmailCheckCode(email);
        return Msg.success();
    }

    @PostMapping("/password")
    public Msg resetPassword(@RequestBody User user) {
        String checkCode = emailSender.getEmailCheckCode(user.getEmail());
        if (checkCode == null || !checkCode.equals(user.getEmailCheckCode())) {
          return  Msg.fail("邮箱验证码错误");
        }
        if(userService.updateUserPasswordByEmail(user) == null){
            return Msg.fail("更新失败，请稍后重试");
        }
        return Msg.success();
    }


}
