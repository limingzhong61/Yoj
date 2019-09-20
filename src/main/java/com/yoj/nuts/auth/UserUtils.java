package com.yoj.nuts.auth;

import com.yoj.web.bean.User;
import com.yoj.web.bean.UserDetailsImpl;
import org.springframework.security.core.context.SecurityContextHolder;

public class UserUtils {
    public static User getUser(){
        UserDetailsImpl userDetails = null;
        User user = null;
        try{
            userDetails = (UserDetailsImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            user = (User) userDetails.getUser();
        }catch (Exception e){
            e.printStackTrace();
        }
        return user;
    }
}
