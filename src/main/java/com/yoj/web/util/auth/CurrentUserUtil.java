package com.yoj.web.util.auth;

import com.yoj.web.pojo.util.UserDetailsImpl;
import com.yoj.web.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.Collection;

@Service
@Slf4j
public class CurrentUserUtil {
    @Autowired
    UserService userService;


    public UserDetailsImpl getUserDetail(){
        UserDetailsImpl userDetails = null;
        try{
            userDetails = (UserDetailsImpl)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        }catch (Exception e){
//            e.printStackTrace();
            log.warn("not login,can not get userDetail");
        }
        return userDetails;
    }

    public Collection<? extends GrantedAuthority>  getAuthorities(){
        return SecurityContextHolder.getContext().getAuthentication().getAuthorities();
    }
}
