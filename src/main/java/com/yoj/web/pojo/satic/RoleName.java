package com.yoj.web.pojo.satic;

import org.springframework.security.core.authority.SimpleGrantedAuthority;

public class RoleName {
    //because equals method in authorities,springSecurity have a prefix with ROLE_
    public static SimpleGrantedAuthority ADMIN = new SimpleGrantedAuthority("ADMIN");
    public static SimpleGrantedAuthority USER = new SimpleGrantedAuthority("USER");
}
