package com.yoj.service;

import com.alibaba.fastjson.JSON;
import com.yoj.constant.consist.Constant;
import com.yoj.model.dto.JudgeSource;
import com.yoj.model.entity.Solution;
import com.yoj.utils.AppInfo;
import com.yoj.utils.HttpUtil;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.entity.StringEntity;
import org.apache.http.message.BasicHeader;
import org.jasypt.encryption.StringEncryptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class JudgeService {
    @Autowired
    private HttpUtil httpUtil;
    @Autowired
    private StringEncryptor encryptor;
    @Autowired
    private SolutionService solutionService;
    @Autowired
    private AppInfo appInfo;

    public void judge(JudgeSource judgeSource) {
        String jsonString = JSON.toJSONString(judgeSource);
        StringEntity entity = new StringEntity(jsonString, "UTF-8");
        List<BasicHeader> headers = new ArrayList<>();
        headers.add(new BasicHeader("Content-Type","application/json;charset=utf8"));
        headers.add(getJudgePermitHeader());
        CloseableHttpResponse response = httpUtil.doPostWithRequestBody(appInfo.getJudgeUrl(),
                entity, headers);
        // only local have return info
        if(appInfo.isLocal()){
            Solution solution = (Solution)response.getEntity();
            solutionService.updateAfterJudge(solution);
        }
    }

    public BasicHeader getJudgePermitHeader(){
        String encrypt = encryptor.encrypt(Constant.JudgePermit);
        return new BasicHeader(Constant.JudgePermit, encrypt);
    }


}



