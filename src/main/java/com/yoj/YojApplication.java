package com.yoj;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.scheduling.annotation.EnableAsync;
@EnableAsync
//@EnableScheduling
@EnableCaching
@MapperScan(value = "com.yoj.web.dao")
@SpringBootApplication
public class YojApplication {

    public static void main(String[] args) {
        SpringApplication.run(YojApplication.class, args);
    }

}
