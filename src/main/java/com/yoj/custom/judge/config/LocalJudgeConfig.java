package com.yoj.custom.judge.config;

import com.yoj.custom.judge.Judge;
import com.yoj.custom.judge.util.ExecutorUtil;
import com.yoj.custom.judge.util.ProblemFileUtil;
import com.yoj.custom.judge.util.impl.LocalExecutor;
import com.yoj.custom.judge.util.impl.LocalJudge;
import com.yoj.custom.judge.util.impl.LocalProblemFileUtil;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConditionalOnProperty(prefix="judge",name = "local", havingValue = "true")
@ConditionalOnMissingBean(RemoteJudgeConfig.class)
public class LocalJudgeConfig {

    @Bean
    public ProblemFileUtil localProblemFileUtil(){
        return new LocalProblemFileUtil();
    }

    @Bean
    public ExecutorUtil localExecutorUtil(){
        return new LocalExecutor();
    }

    @Bean
    public Judge localJudge(){
        return new LocalJudge();
    }

}
