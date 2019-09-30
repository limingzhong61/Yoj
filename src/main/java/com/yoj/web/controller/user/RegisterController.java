package com.yoj.web.controller.user;

import com.yoj.web.bean.util.Msg;
import com.yoj.web.bean.User;
import com.yoj.web.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Random;
import java.util.concurrent.TimeUnit;

/**
 * @Description: 注册控制层
 * @Author: lmz
 * @Date: 2019/9/27
 */
@RestController
@RequestMapping("/u/r")
public class RegisterController {
    @Autowired
    UserService userService;

    @Autowired
    JavaMailSenderImpl mailSender;

    @Autowired
    StringRedisTemplate stringRedisTemplate;

    @Value("${spring.mail.username}")
    private String from;

    @RequestMapping("/register")
    public Msg register(User user) {
        String checkCode = stringRedisTemplate.opsForValue().get(user.getEmail());
        Msg msg = new Msg();
        msg.setSuccess(true);
        if (checkCode == null || !checkCode.equals(user.getEmailCheckCode())) {
            msg.setSuccess(false);
            msg.add("emailCheckCode", "邮箱验证码错误");
        }
        if (userService.getUserByName(user.getUserName()) != null) {
            msg.setSuccess(false);
            msg.add("userName", "用户名已存在");
        }
        if (!msg.isSuccess()) {
            return msg;
        }
        if (userService.insertUserUseCache(user) == null) {
            return Msg.fail().add("other", "系统错误");
        }
        stringRedisTemplate.delete(user.getEmail());
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

    @RequestMapping("/getEmailCheckCode/{email}")
    public Msg getCheckCode(@PathVariable("email") String email) {
        if (userService.queryExistByEmail(email)) {
            return Msg.fail("邮箱已被注册");
        }
        SimpleMailMessage message = new SimpleMailMessage();
        String checkCode = String.valueOf(new Random().nextInt(899999) + 100000);
        //邮件设置
        message.setSubject("YOJ注册验证码");
        message.setText("您的注册验证码为：" + checkCode);

        message.setTo(email);
        message.setFrom(from);
        mailSender.send(message);
        stringRedisTemplate.opsForValue().set(email, checkCode, 60 * 10, TimeUnit.SECONDS);//向redis里存入数据和设置缓存时间
        return Msg.success().add("checkCode", checkCode);
    }
}
