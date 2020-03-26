package com.yoj.utils.cache;

import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.stereotype.Component;

/**
* @Description: manage user cache
* @Param:
* @return:
* @Author: lmz
* @Date: 2019/10/27
*/
@CacheConfig(cacheNames = "user")
@Component
@Slf4j
public class UserCacheUtil {

    @CacheEvict()
    public void delById(Integer userId) {
        log.info("delete cache by id");
    }
}
