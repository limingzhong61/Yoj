package com.yoj.web.controller;

import com.yoj.web.bean.Msg;
import com.yoj.web.bean.User;
import com.yoj.web.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Map;

@Controller
@RequestMapping("/u")
public class UserController {
    @Autowired
    UserService userService;

    // Login form with error
    @RequestMapping("/login-error")
    public String loginError(Model model) {
        model.addAttribute("loginError", true);
        return "user/login";
    }

    //
    @PostMapping("/register")
    public String register(User user, Map<String, Object> map) {
        Msg msg = userService.insertUser(user);
        if (msg.isSuccess()) {
            map.put("msg", "注册成功，请登录");
            return "user/login";
        }
        map.put("msg",msg.getMsg());
        return "user/register";
    }
}
