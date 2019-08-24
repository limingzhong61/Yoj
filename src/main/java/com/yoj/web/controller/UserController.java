package com.yoj.web.controller;

import com.yoj.web.bean.User;
import com.yoj.web.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

@Controller
@RequestMapping("/user")
public class UserController {
    @Autowired
    UserService userSevice;

    @PostMapping("/login")
    public String login(User user, Map<String, Object> map,HttpServletRequest request) {
        user = userSevice.queryUserExist(user);
        if (user != null) {
            request.getSession().setAttribute("user",user);
            return "redirect:/home.html";
        }
        map.put("msg", "用户名密码错误");
        return "login";
    }

    //
    @PostMapping("/register")
    public String register(User user, Map<String, Object> map) {
        if (userSevice.insertUser(user)) {
            map.put("msg", "注册成功，请登录");
            return "login";
        }
        return "register";
    }

    @GetMapping("/logout")
    public String logout(HttpServletRequest request) {
        //设置用户为null
        request.getSession().setAttribute("user",null);
       // map.put("msg", "用户名密码错误");
        return "login";
    }
}
