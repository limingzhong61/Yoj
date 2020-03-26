package com.yoj.controller.user;

import com.yoj.model.entity.User;
import com.yoj.model.vo.Msg;
import com.yoj.utils.cache.EmailCache;
import com.yoj.service.UserService;
import com.yoj.utils.EmailSender;
import com.yoj.utils.VerifyImageUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

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
    @Autowired
    EmailCache emailCache;
    @Autowired
    VerifyImageUtil verifyImageUtil;

    @GetMapping("/emailCode/{email}")
    public Msg getEmailCode(@PathVariable("email") String email) {
        User user = userService.getUserByEmail(email);
        if (user == null) {
            return Msg.fail("该邮箱没有被注册");
        }
        emailSender.sendResetPasswordEmail(email);
        return Msg.success();
    }

    @PostMapping("/password")
    public Msg resetPassword(@RequestBody User user, HttpServletRequest httpServletRequest) {
        if(!verifyImageUtil.verify(httpServletRequest,user.getImageCode())){
            return Msg.fail().add("imageCode","验证码错误");
        }
        String checkCode = emailCache.getEmailCheckCode(user.getEmail());
        if (checkCode == null || !checkCode.equals(user.getEmailCode())) {
          return  Msg.fail().add("emailCode", "邮箱验证码错误");
        }
        if(userService.updateUserPasswordByEmail(user) == null){
            return Msg.fail("更新失败，请稍后重试");
        }
        emailCache.delEmailCheckCode(user.getEmail());
        return Msg.success();
    }


}
