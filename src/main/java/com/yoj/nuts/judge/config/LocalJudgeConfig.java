package com.yoj.nuts.judge.config;

import com.yoj.nuts.judge.util.ExecutorUtil;
import com.yoj.nuts.judge.util.ProblemFileUtil;
import com.yoj.nuts.judge.util.impl.LocalExecutor;
import com.yoj.nuts.judge.util.impl.LocalProblemFileUtil;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConditionalOnProperty(prefix="judge",name = "localJudge", havingValue = "true")
public class LocalJudgeConfig {

    @Bean
    public ProblemFileUtil localProblemFileUtil(){
        return new LocalProblemFileUtil();
    }

    @Bean
    public ExecutorUtil localExecutorUtil(){
        return new LocalExecutor();
    }

}
