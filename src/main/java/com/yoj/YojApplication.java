package com.yoj;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

//使用MapperScan批量扫描所有的Mapper接口；
@MapperScan(value = "com.yoj.web.mapper")
@SpringBootApplication
public class YojApplication {

    public static void main(String[] args) {
        SpringApplication.run(YojApplication.class, args);
    }

}
