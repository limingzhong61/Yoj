package com.yoj.config.judge;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConditionalOnProperty(prefix="judge",name = "local", havingValue = "false")
public class RemoteJudgeConfig {

}
