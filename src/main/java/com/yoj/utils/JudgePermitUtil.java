package com.yoj.utils;

import org.apache.http.message.BasicHeader;
import org.jasypt.encryption.StringEncryptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;

@Service
public class JudgePermitUtil {
    @Autowired
    private StringEncryptor encryptor;

    public BasicHeader getJudgePermitHeader(){
        String encrypt = encryptor.encrypt("Judge-Permit");
        return new BasicHeader("Judge-Permit", encrypt);
    }

    public boolean validateJudgePermit(HttpServletRequest request) {
        String header = request.getHeader("Judge-Permit");
        return header != null && encryptor.decrypt(header).equals("Judge-Permit");
    }
}
