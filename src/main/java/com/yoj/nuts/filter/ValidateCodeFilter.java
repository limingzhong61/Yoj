package com.yoj.nuts.filter;

import com.yoj.nuts.config.security.ValidateImageFailureHandler;
import com.yoj.web.util.ValidateImageUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.ServletRequestBindingException;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Slf4j
@Component
public class ValidateCodeFilter extends OncePerRequestFilter {
    @Autowired
    private ValidateImageFailureHandler authenticationFailureHandler;
    @Autowired
    private ValidateImageUtil validateImageUtil;
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        if(request.getRequestURI().equals("/login")&&request.getMethod().equalsIgnoreCase("post")){
            try {
                validate(request);
            } catch (VerifyCodeException e) {
                authenticationFailureHandler.onAuthenticationFailure(request,response,e);
                return;
            }
        }
        // 3. 校验通过，就放行
        filterChain.doFilter(request, response);
    }

    /* 验证保存在session的验证码和表单提交的验证码是否一致 */
    private void validate(HttpServletRequest request) throws ServletRequestBindingException {
        if(!validateImageUtil.validate(request)){
            throw new VerifyCodeException("验证码不正确！");
        }
        request.getSession().removeAttribute(request.getParameter("tryCode"));
    }

    /**
     * 声明一个验证码异常，用于抛出特定的验证码异常
     */
    public class VerifyCodeException extends AuthenticationException {
        public VerifyCodeException(String msg) {
            super(msg);
        }
    }
}
