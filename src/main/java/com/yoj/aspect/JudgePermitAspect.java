package com.yoj.aspect;

import com.yoj.utils.JudgePermitUtil;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

@Aspect
@Component
@Slf4j
public class JudgePermitAspect {
    @Autowired
    private JudgePermitUtil judgePermitUtil;
    @Pointcut("@annotation(com.yoj.annotation.JudgePermitAnnotation)")
    public void judgePermit() {
    }

    /**
     * validate if has right header of Judge-Permit
     */
    @Before("judgePermit()")
    public void doBefore(JoinPoint joinPoint) {
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();
        if(!judgePermitUtil.validateJudgePermit(request)){
            log.info("wrong head");
            throw new RuntimeException("illegal request");
        }
    }
}
