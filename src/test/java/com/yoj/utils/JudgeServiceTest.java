package com.yoj.utils;

import com.alibaba.fastjson.JSON;
import com.yoj.model.dto.JudgeSource;
import com.yoj.service.JudgeService;
import org.apache.http.entity.StringEntity;
import org.apache.http.message.BasicHeader;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class JudgeServiceTest {
    @Autowired
    JudgeService judgeService;
    @Autowired
    HttpUtil httpUtil;

    @Test
    public void test(){
        JudgeSource judgeSource = new JudgeSource();
        judgeSource.setMemoryLimit(1);
        judgeSource.setCode("c++");
        judgeSource.setSolutionId(1);
        judgeSource.setTimeLimit(100);
        judgeSource.setLanguage(1);
        judgeSource.setProblemId(2);


        // 我这里利用阿里的fastjson，将Object转换为json字符串;
        // (需要导入com.alibaba.fastjson.JSON包)
        String jsonString = JSON.toJSONString(judgeSource);
        StringEntity entity = new StringEntity(jsonString, "UTF-8");
        List<BasicHeader> headers = new ArrayList<>();
        headers.add(new BasicHeader("Content-Type", "application/json;charset=utf8"));
//        headers.add(judgeService.getJudgePermitHeader());
        httpUtil.doPostWithRequestBody("http://localhost:11111/judge",
                entity, headers);
    }
}