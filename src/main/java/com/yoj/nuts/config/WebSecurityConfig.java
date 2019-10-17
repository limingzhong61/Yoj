package com.yoj.nuts.config;

import com.yoj.web.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    UserService userService;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .authorizeRequests()
                .antMatchers("/user/login", "/webjars/**","/assets/**",
                        "/user/register","/u/login-error","/u/r/**")
                .permitAll()
                .anyRequest().authenticated();
        http
                .formLogin()
                .usernameParameter("userName")
                .passwordParameter("password")
                .loginPage("/user/login")
                .failureUrl("/user/login-error");
        http
                .rememberMe()
                .rememberMeParameter("remember");
        http
                .logout()
                .logoutSuccessUrl("/user/login");
        //关闭跨域攻击
        http.csrf().disable();
        // 省略其他代码；
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        //注入userDetailsService，需要实现userDetailsService接口
        //new BCryptPasswordEncoder() 加密，密码？？
        auth.userDetailsService(userService);
    }

}
