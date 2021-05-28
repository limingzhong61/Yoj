package com.yoj.utils;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
@Data
public class AppInfo {
    @Value("${judge.url}")
    private String judgeUrl;

    @Value("${judge.local}")
    private String local;

    @Value("${spring.resources.static-locations}")
    private String storeRootLocationStr;

    public String getStoreRootLocationStr(){
        return this.storeRootLocationStr.substring(5);
    }

    public boolean isLocal(){
        return "local".equals(this.local);
    }
}
