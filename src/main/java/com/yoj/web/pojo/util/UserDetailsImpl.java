package com.yoj.web.pojo.util;

import com.yoj.web.pojo.User;
import lombok.ToString;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@ToString
public class UserDetailsImpl implements UserDetails {
    private String username;
    private String password;
    //包含着用户对应的所有Privilege，在使用时调用者给对象注入Privileges
    private String roles;

    public void setPrivilege(String roles) {
        this.roles = roles;
    }

    //无参构造
    public UserDetailsImpl() {
    }

    //用User构造
    public UserDetailsImpl(User user) {
        this.username = user.getUserName();
        this.password = user.getPassword();
        this.roles = user.getRole();
    }

    public String getRoles() {
        return roles;
    }

    @Override
    //返回用户所有角色的封装，一个Privilege对应一个GrantedAuthority
    public Collection<? extends GrantedAuthority> getAuthorities() {
        List<GrantedAuthority> authorities = new ArrayList<>();
        if(roles != null){
            for (String role : roles.split(",")) {
                authorities.add(new SimpleGrantedAuthority(role));
            }
        }
        //每个人都默认为普通用户
//        authorities.add(new SimpleGrantedAuthority(RoleName.NORMAL.toString()));

        return authorities;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    //判断账号是否已经过期，默认没有过期
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    //判断账号是否被锁定，默认没有锁定
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    //判断信用凭证是否过期，默认没有过期
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    //判断账号是否可用，默认可用
    public boolean isEnabled() {
        return true;
    }

}
