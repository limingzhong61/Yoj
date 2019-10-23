package com.yoj.nuts.config.security;


import com.alibaba.fastjson.JSON;
import com.yoj.web.bean.util.Msg;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class JSONAuthenticationFailureHandler implements AuthenticationFailureHandler {

    @Override
    public void onAuthenticationFailure(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, AuthenticationException e) throws IOException, ServletException {
        Msg msg = Msg.fail("Login Failure!");
        msg.setState(400);
        httpServletResponse.getWriter().write(JSON.toJSONString(msg));
    }
}

