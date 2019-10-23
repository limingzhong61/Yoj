package com.yoj.nuts.config.security;

import com.alibaba.fastjson.JSON;
import com.yoj.web.bean.util.Msg;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class JSONAccessDeniedHandler implements AccessDeniedHandler {

    @Override
    public void handle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, AccessDeniedException e) throws IOException, ServletException {
        Msg msg = Msg.fail("Need Authorities!");
        msg.setState(300);
        httpServletResponse.getWriter().write(JSON.toJSONString(msg));
    }
}

