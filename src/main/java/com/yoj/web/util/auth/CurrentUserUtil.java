package com.yoj.web.util.auth;

import com.yoj.web.pojo.User;
import com.yoj.web.pojo.util.UserDetailsImpl;
import com.yoj.web.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.Collection;

@Service
public class CurrentUserUtil {
    @Autowired
    UserService userService;

    public User getUser(){
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

    public Collection<? extends GrantedAuthority>  getAuthorities(){
        return SecurityContextHolder.getContext().getAuthentication().getAuthorities();
    }
}
