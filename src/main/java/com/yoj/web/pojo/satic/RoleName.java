package com.yoj.web.pojo.satic;

import org.springframework.security.core.authority.SimpleGrantedAuthority;

public class RoleName {
    //because equals method in authorities
    public static SimpleGrantedAuthority ADMIN = new SimpleGrantedAuthority("系统管理员");
    public static SimpleGrantedAuthority NORMAL = new SimpleGrantedAuthority("普通用户");
}
