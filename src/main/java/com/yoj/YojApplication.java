package com.yoj;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@MapperScan(value = "com.yoj.web.dao")
@SpringBootApplication
public class YojApplication {

    public static void main(String[] args) {
        SpringApplication.run(YojApplication.class, args);
    }

}
