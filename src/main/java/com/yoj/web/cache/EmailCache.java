package com.yoj.web.cache;

import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;

@Component
@CacheConfig(cacheNames = "email")
@Slf4j
public class EmailCache {

    /**
     * @Description: 设置和获取缓存
     * @Param: [email]
     * @return: java.lang.String
     * @Author: lmz
     * @Date: 2019/10/28
     */
    @Cacheable(unless = "#result == null")
    public String getEmailCheckCode(String email) {
        return null;
    }

    @CachePut(key="#email",unless = "#result == null")
    public String setEmailCheckCode(String email, String checkCode) {
        log.info("set email checkCode ");
        return checkCode;
    }

    @CacheEvict
    public void delEmailCheckCode(String email) {
        log.info("delete email checkCode");
    }
}
