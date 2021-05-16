package com.yoj.model.pojo.util;

import com.yoj.model.entity.User;
import lombok.ToString;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Collections;

@ToString
public class UserDetailsImpl implements UserDetails {
    private Integer useId;
    private String username;
    private String password;
    //包含着用户对应的所有Privilege，在使用时调用者给对象注入Privileges
    private Collection<? extends GrantedAuthority> authorities;

    //无参构造
    public UserDetailsImpl() {
    }

    //用User构造
    public UserDetailsImpl(User user) {
        useId = user.getUserId();
        username = user.getUsername();
        password = user.getPassword();
        authorities = Collections.singleton(new SimpleGrantedAuthority(user.getRole()));
    }

    // 获取权限信息，目前博主只会拿来存角色。。
    //返回用户所有角色的封装，一个Privilege对应一个GrantedAuthority
    //每个人都默认为普通用户
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    public Integer getUserId() {
        return useId;
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
    // 账号是否未过期，默认是false，记得要改一下
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    //判断账号是否被锁定，默认没有锁定
    // 账号是否未锁定，默认是false，记得也要改一下
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    //判断信用凭证是否过期，默认没有过期
    // 账号凭证是否未过期，默认是false，记得还要改一下
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    //判断账号是否可用，默认可用
    public boolean isEnabled() {
        return true;
    }

}
