package com.yoj.service;

import com.yoj.mapper.DownloadCntMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
@CacheConfig(cacheNames = "downloadCnt")
public class DownloadCntService {
    @Autowired
    private DownloadCntMapper downloadCntMapper;

    @Cacheable(key = "#uid")
    public Integer getDownloadCnt(Integer uid) {
        Integer downloadCnt = downloadCntMapper.getDownloadCnt(uid);
        if (downloadCnt == null) {
            Integer insert = downloadCntMapper.insert(uid);
            if (insert == 0) {
                return null;
            }
            return 0;
        }
        return downloadCnt;
    }

    @CachePut(key = "#userId", unless = "#result == null")
    public Integer update(Integer userId, Integer downloadCnt) {
        Integer update = downloadCntMapper.updateById(userId);
        if (update == 0) {
            return null;
        }
        return downloadCnt + 1;
    }

    /**
     * Delete all download data everyday.
     */
    @Scheduled(cron = "0 0 0 * * *")
    public void deleteAllCnt() {
        downloadCntMapper.deleteAll();
    }

}
