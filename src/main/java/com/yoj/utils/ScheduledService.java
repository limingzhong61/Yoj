package com.yoj.utils;

import com.yoj.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

/**
* @Description:  不在需要，内存原因
* @Author: lmz
*/
@Service
public class ScheduledService { 
    @Autowired
    public UserService userService;

    /**
    * @Description: 定期更新用户排名 
    * @Param: [] 
    * @return: void 
    * @Author: lmz
    * @Date: 2019/10/25 
    */ 
    @Scheduled(cron = "0 * * * * *")
    public void updateUserRank() {

    }
}
