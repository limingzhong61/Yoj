package com.yoj.nuts.config.security;


import com.alibaba.fastjson.JSON;
import com.yoj.web.bean.util.Msg;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class JSONAuthenticationSuccessHandler implements AuthenticationSuccessHandler {

    @Override
    public void onAuthenticationSuccess(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Authentication authentication) throws IOException, ServletException {
        Msg msg = Msg.success();
        msg.setState(200);
        httpServletResponse.getWriter().write(JSON.toJSONString(msg));
    }
}
