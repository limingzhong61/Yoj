package com.yoj.web.controller;


import com.yoj.web.util.VerifyImageUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@RestController
@RequestMapping("/verify")
public class VerifiedImageController {

    @Autowired
    VerifyImageUtil verifyImageUtil;

    /**
     * 2、生成验证码
     * @param httpServletRequest
     * @param httpServletResponse
     * @throws Exception
     */
    @GetMapping("/image")
    public void getImage(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse)
            throws Exception {
        verifyImageUtil.getImage(httpServletRequest,httpServletResponse);
    }
}

