package com.yoj.custom.properties;

import com.yoj.custom.properties.judge_bean.Linux;
import com.yoj.custom.properties.judge_bean.Windows;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
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
@Setter
@Getter
@ToString
public class JudgeProperties {
    //运行平台:linux/windows
    private String platform;

    private Linux linux;
//保存题目的路径，必须存在，path必须有‘/’结尾,不能保存在/tmp目录下，重启会被删除文件
    private String judgeScriptPath;

    private String ip;

    private String userName;

    private String password;

    private Windows windows;
}
