package com.yoj.utils.auth;

import com.yoj.model.pojo.util.UserDetailsImpl;
import com.yoj.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.Collection;

@Service
@Slf4j
public class UserUtil {
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

    public boolean isAdmin(){
        return AuthorityUtils.authorityListToSet(getUserDetail().getAuthorities()).contains("ADMIN");
    }
}
