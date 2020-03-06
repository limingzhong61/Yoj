package com.yoj.custom.judge.config;

import com.yoj.custom.judge.Judge;
import com.yoj.custom.judge.util.ExecutorUtil;
import com.yoj.custom.judge.util.ProblemFileUtil;
import com.yoj.custom.judge.util.SSH2Util;
import com.yoj.custom.judge.util.impl.RemoteExecutor;
import com.yoj.custom.judge.util.impl.RemoteJudge;
import com.yoj.custom.judge.util.impl.RemoteProblemFileUtil;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConditionalOnProperty(prefix="judge",name = "local", havingValue = "false")
public class RemoteJudgeConfig {

    @Bean
    public ProblemFileUtil remoteProblemFileUtil(){
        return new RemoteProblemFileUtil();
    }

    @Bean
    public ExecutorUtil remoteExecutor(){
        return new RemoteExecutor();
    }
    @Bean
    public Judge remoteJudge(){
        return new RemoteJudge();
    }

    // use for communicate with remote server
    @Bean
    public SSH2Util ssh2Util(){
        return new SSH2Util();
    }

}
