package com.yoj.nuts.auth;

import com.yoj.web.bean.User;
import com.yoj.web.bean.util.UserDetailsImpl;
import com.yoj.web.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
public class UserUtils {
    @Autowired
    UserService userService;

    public User getCurrentUser(){
        UserDetailsImpl userDetails = null;
        User user = null;
        try{
            SecurityContext context = SecurityContextHolder.getContext();
            Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            user = userService.getUserByName(principal.toString());
        }catch (Exception e){
            e.printStackTrace();
        }
        return user;
    }
}
