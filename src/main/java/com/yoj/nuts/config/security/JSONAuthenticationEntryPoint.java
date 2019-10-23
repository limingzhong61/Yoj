package com.yoj.nuts.config.security;


import com.alibaba.fastjson.JSON;
import com.yoj.web.bean.util.Msg;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class JSONAuthenticationEntryPoint implements AuthenticationEntryPoint {

    @Override
    public void commence(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, AuthenticationException e) throws IOException, ServletException {
        Msg msg = Msg.fail("Need Authorities!");
        msg.setState(0);

        httpServletResponse.getWriter().write(JSON.toJSONString(msg));
    }
}
