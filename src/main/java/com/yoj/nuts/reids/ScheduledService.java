package com.yoj.nuts.reids;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
public class ScheduledService {
    @Autowired
    StringRedisTemplate stringRedisTemplate;

    @Scheduled(cron = "0 * * * * *")
    public void timer() {
        System.out.println("heartbeat");
        stringRedisTemplate.opsForValue().get("heartbeat");
    }
}
