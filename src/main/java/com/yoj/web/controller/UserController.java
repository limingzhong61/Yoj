package com.yoj.web.controller;

import com.yoj.nuts.auth.UserUtils;
import com.yoj.web.bean.User;
import com.yoj.web.bean.util.Msg;
import com.yoj.web.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/user")
public class UserController {
    @Autowired
    UserService userService;
    @Autowired
    StringRedisTemplate stringRedisTemplate;
    @Autowired
    private UserUtils userUtils;

    // Login form with error
    @RequestMapping("/login-error")
    public String loginError(Model model) {
        model.addAttribute("loginError", true);
        return "user/login";
    }

    @GetMapping("/info")
    @ResponseBody
    public Msg getUserInfo(){
        User user = userUtils.getCurrentUser();
        if(user == null){
            return Msg.fail();
        }
        user.setPassword("");
       return  Msg.success().add("user",user);

    }


}
