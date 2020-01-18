package com.yoj.custom.security;


import com.alibaba.fastjson.JSON;
import com.yoj.custom.enums.ExceptionEnum;
import com.yoj.custom.filter.LoginValidateFilter;
import com.yoj.web.pojo.satic.RoleName;
import com.yoj.web.pojo.util.Msg;
import com.yoj.web.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.core.GrantedAuthorityDefaults;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Configuration
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SpringSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    LoginValidateFilter validateCodeFilter;

    @Autowired
    UserService userService;

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userService);
//                .passwordEncoder(bCryptPasswordEncoder());
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        //关闭跨域攻击
        http.csrf().disable();

//        http.exceptionHandling().authenticationEntryPoint(myAuthenticationEntryPoint).accessDeniedHandler(myAccessDeniedHandler);


        http.httpBasic()//  未登陆时返回 JSON 格式的数据给前端（否则为 html）
                .authenticationEntryPoint((HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) -> {
                    response.getWriter().write(JSON.toJSONString(Msg.fail(ExceptionEnum.NEED_LOGIN)));
                })
                .and()
                .authorizeRequests().antMatchers("/problem/add", "problem/alter").hasRole(RoleName.ADMIN.toString())// 其他 url 需要身份认证
                .antMatchers("/solution/submit", "/solution/detail").authenticated()
                .anyRequest().permitAll();

        http.formLogin()  //开启登录
                //如果不开启，会导致前端代理proxy定向失败
                .successHandler( // 登录成功返回的 JSON 格式数据给前端（否则为 html）
                        (HttpServletRequest request, HttpServletResponse response, Authentication authentication) -> {
                            Msg msg = Msg.success();
                            msg.setState(200);
                            response.getWriter().write(JSON.toJSONString(msg));
//                            SecurityContextHolder.getContext().setAuthentication(authentication);
                        }
                )//  登录失败返回的 JSON 格式数据给前端（否则为 html）
                .failureHandler((HttpServletRequest request,
                                 HttpServletResponse response, AuthenticationException exception) -> {
                    Msg msg = Msg.fail("用户名/密码错误");
                    msg.setState(400);
                    response.setCharacterEncoding("utf-8");
                    response.getWriter().write(JSON.toJSONString(msg));
                })
                .permitAll();

        http.logout()//退出成功
                .logoutSuccessHandler((HttpServletRequest request, HttpServletResponse response, Authentication authentication) ->
                {
                    response.setCharacterEncoding("utf-8");
                    response.setContentType("text/json;charset=utf-8");
                    response.getWriter().write(JSON.toJSONString(Msg.success()));
                })
                .permitAll();
        // remember-me
        http.rememberMe().rememberMeParameter("rememberMe");
        //exception handing,return json when not login or have not enough authority
        http.exceptionHandling().authenticationEntryPoint((HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) -> {
            response.setContentType("text/json;charset=utf-8");
            response.getWriter().write(JSON.toJSONString(Msg.fail(ExceptionEnum.NEED_LOGIN)));
        }).accessDeniedHandler((HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException) -> {
            response.setContentType("text/json;charset=utf-8");
            response.getWriter().write(JSON.toJSONString(Msg.fail(ExceptionEnum.NOT_ACCESS)));
        }); // 无权访问 JSON 格式的数据
        /* 添加验证码过滤器 */
        http.addFilterBefore(validateCodeFilter, UsernamePasswordAuthenticationFilter.class);
    }


    // don't have useful
    @Bean
    GrantedAuthorityDefaults grantedAuthorityDefaults() {
        return new GrantedAuthorityDefaults(""); // Remove the ROLE_ prefix
    }

}

