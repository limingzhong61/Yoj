package com.yoj.nuts.judge.config;

import com.yoj.nuts.judge.util.ExecutorUtil;
import com.yoj.nuts.judge.util.ProblemFileUtil;
import com.yoj.nuts.judge.util.impl.RemoteExecutor;
import com.yoj.nuts.judge.util.impl.RemoteProblemFileUtil;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConditionalOnProperty(prefix="judge",name = "localJudge", havingValue = "false")
public class RemoteJudgeConfig {

    @Bean
    public ProblemFileUtil remoteProblemFileUtil(){
        return new RemoteProblemFileUtil();
    }

    @Bean
    public ExecutorUtil remoteExecutor(){
        return new RemoteExecutor();
    }

}
