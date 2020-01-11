package com.yoj.web.controller.user;

import com.yoj.web.pojo.User;
import com.yoj.web.pojo.util.Msg;
import com.yoj.web.cache.EmailCache;
import com.yoj.web.service.UserService;
import com.yoj.web.util.EmailSender;
import com.yoj.web.util.VerifyImageUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.List;

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
    EmailCache emailCache;
    @Autowired
    StringRedisTemplate stringRedisTemplate;

    @Autowired
    VerifyImageUtil verifyImageUtil;

    @PostMapping("/register")
    public Msg register(@RequestBody @Valid User user, BindingResult bindingResult,
                        HttpServletRequest httpServletRequest) {

        Msg msg = new Msg();
        msg.setSuccess(true);

        if (!verifyImageUtil.verify(httpServletRequest, user.getImageCode())) {
            return Msg.fail().add("imageCode", "验证码错误");
        }
        if (bindingResult.hasErrors()) {
            msg.setSuccess(false);
            List<FieldError> fieldErrors = bindingResult.getFieldErrors();
            for(FieldError fieldError: fieldErrors){
                msg.add(fieldError.getField(),fieldError.getDefaultMessage());
//                System.out.println(fieldError.getField() + ":" + fieldError.getDefaultMessage());
            }
            return msg;
        }
        if (userService.getUserByName(user.getUsername() )!= null) {
            msg.setSuccess(false);
            msg.add("userName", "用户名已存在");
        }
        String checkCode = emailCache.getEmailCheckCode(user.getEmail());
        if (checkCode == null || !checkCode.equals(user.getEmailCode())) {
            msg.setSuccess(false);
            msg.add("emailCode", "邮箱验证码错误");
        }
        if (!msg.isSuccess()) {
            return msg;
        }
        if (userService.insertUserUseCache(user) == null) {
            return Msg.fail("系统错误");
        }
        emailCache.delEmailCheckCode(user.getEmail());
        return Msg.success();
    }


    @GetMapping("/getEmailCheckCode/{email}")
    public Msg getCheckCode(@PathVariable(value = "email") String email) {
//        int i = 1 / 0;
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
