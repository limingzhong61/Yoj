package com.yoj.utils;

import org.jasypt.encryption.StringEncryptor;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class HttpUtilTest {
    @Autowired
    private HttpUtil httpUtil;
    @Autowired
    private StringEncryptor encryptor;

    @Test
    public void doPostWithRequestBody() {
//        JudgeSource judgeSource = new JudgeSource();
//        judgeSource.setMemoryLimit(1);
//        judgeSource.setCode("c++");
//        judgeSource.setSolutionId(1);
//        judgeSource.setTimeLimit(100);
//        judgeSource.setLanguage(1);
//        judgeSource.setProblemId(2);
//
//
//        // 我这里利用阿里的fastjson，将Object转换为json字符串;
//        // (需要导入com.alibaba.fastjson.JSON包)
//        String jsonString = JSON.toJSONString(judgeSource);
//        StringEntity entity = new StringEntity(jsonString, "UTF-8");
//        List<BasicHeader> headers = new ArrayList<>();
//        headers.add(new BasicHeader("Content-Type", "application/json;charset=utf8"));
//        headers.add(new BasicHeader("Content-Type", "application/json;charset=utf8"));
//        String encrypt = encryptor.encrypt(Constant.JudgePermit);
//        headers.add(new BasicHeader(Constant.JudgePermit, encrypt));
//        httpUtil.doPostWithRequestBody("http://localhost:11111/judge",
//                entity, headers);
    }

}