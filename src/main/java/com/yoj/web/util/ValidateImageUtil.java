package com.yoj.web.util;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;

@Component
@Slf4j
public class ValidateImageUtil {

    public boolean validate(HttpServletRequest httpServletRequest) {
        String rightCode = (String) httpServletRequest.getSession().getAttribute("rightCode");
        String tryCode = httpServletRequest.getParameter("tryCode");
        log.info("rightCode:"+rightCode+" ———— tryCode:"+tryCode);
        if(rightCode == null || tryCode == null){
            return false;
        }
        return rightCode.equals(tryCode);
    }
}
