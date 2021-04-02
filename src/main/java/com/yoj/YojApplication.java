package com.yoj;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@EnableAsync
@EnableScheduling
@EnableCaching //close cache because it's not needed for now.
@MapperScan(value = "com.yoj.mapper")
@SpringBootApplication
@EnableTransactionManagement(proxyTargetClass=true)
public class YojApplication {
    public static void main(String[] args) {
        SpringApplication.run(YojApplication.class, args);
    }
}
