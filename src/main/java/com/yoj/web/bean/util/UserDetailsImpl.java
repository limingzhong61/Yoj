package com.yoj.web.bean.util;

import com.yoj.web.bean.Privilege;
import com.yoj.web.bean.User;
import lombok.ToString;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@ToString
public class UserDetailsImpl implements UserDetails{
    private String username;
    private String password;
    //包含着用户对应的所有Privilege，在使用时调用者给对象注入Privileges
    private List<Privilege> privilege;

    public void setPrivilege(List<Privilege> privilege) {
        this.privilege = privilege;
    }

    //无参构造
    public UserDetailsImpl() {
    }

    //用User构造
    public UserDetailsImpl(User user) {
        this.username = user.getUserName();
        this.password = user.getPassword();
    }

    //用User和List<Privilege>构造
    public UserDetailsImpl(User user,List<Privilege> Privileges) {
        this.username = user.getUserName();
        this.password = user.getPassword();
        this.privilege = Privileges;
    }

    public List<Privilege> getPrivilege()
    {
        return privilege;
    }

    @Override
    //返回用户所有角色的封装，一个Privilege对应一个GrantedAuthority
    public Collection<? extends GrantedAuthority> getAuthorities() {
        List<GrantedAuthority> authorities = new ArrayList<>();
        for(Privilege Privilege : privilege) {
            authorities.add(new SimpleGrantedAuthority(Privilege.getRight()));
        }
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
