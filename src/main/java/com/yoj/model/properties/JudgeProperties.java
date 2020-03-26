package com.yoj.model.properties;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

/**
 * @Description: 除此配置外，还需在修改judge中指定ExecutorUtil的注入类
 * @Param:
 * @return:
 * @Author: lmz
 * @Date: 2019/10/20
 */
@Component
@ConfigurationProperties(prefix = "judge")
@PropertySource("classpath:judge.properties")
@Data
public class JudgeProperties {
    private String linuxFilePath;
    //保存题目的路径，必须存在，path必须有‘/’结尾
    private String windowsFilePath;
    @Value("${judge.local}")
    private String local;

    public boolean isLocal(){
        return Boolean.getBoolean(this.local);
    }
}
