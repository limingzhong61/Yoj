package com.yoj.custom.filter;

import com.yoj.custom.security.ValidateImageFailureHandler;
import com.yoj.web.util.VerifyImageUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Component;
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
    private VerifyImageUtil validateImageUtil;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        if (request.getRequestURI().equals("/login") && request.getMethod().equalsIgnoreCase("post")) {
            try {
                /* 验证保存在session的验证码和表单提交的验证码是否一致 */
                if (!validateImageUtil.verify(request)) {
                    /**
                     * 声明一个验证码异常，用于抛出特定的验证码异常
                     */
                    throw new AuthenticationException("验证码不正确！"){};
                }
                request.getSession().removeAttribute(request.getParameter("tryCode"));
            } catch (AuthenticationException e) {
                authenticationFailureHandler.onAuthenticationFailure(request, response, e);
                return;
            }
        }
        // 3. 校验通过，就放行
        filterChain.doFilter(request, response);
    }
}
