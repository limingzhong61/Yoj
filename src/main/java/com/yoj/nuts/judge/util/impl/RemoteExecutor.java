package com.yoj.nuts.judge.util.impl;

import ch.ethz.ssh2.Connection;
import ch.ethz.ssh2.Session;
import com.yoj.nuts.judge.bean.ExecMessage;
import com.yoj.nuts.judge.util.ExecutorUtil;
import com.yoj.nuts.properties.JudgeProperties;
import lombok.ToString;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.IOException;

/**
 * @author lmz 远程连接linux使用
 */
//@Service
@ToString
public class RemoteExecutor implements ExecutorUtil {

    @Autowired
    JudgeProperties judgeProperties;

    private static final Logger log = LoggerFactory.getLogger(RemoteExecutor.class);

    private static Connection conn;

    /**
     * 登录主机
     *
     * @return 登录成功返回true，否则返回false
     */
    public static Connection login(String ip, String userName, String userPwd) {
        boolean flg = false;
        Connection conn = null;
        try {
            conn = new Connection(ip);
            conn.connect();// 连接
            flg = conn.authenticateWithPassword(userName, userPwd);// 认证

            if (flg) {
                RemoteExecutor.conn = conn;
                log.info("=========登录成功=========" + conn);
                return conn;
            }
        } catch (IOException e) {
            log.error("=========登录失败=========" + e.getMessage());
            e.printStackTrace();
        }
        return conn;
    }

    /**
     *
     */
    @Override
    public ExecMessage execute(String cmd) {
        Session session = null;
        try {
            if (conn == null) {
                login(judgeProperties.getIp(), judgeProperties.getUserName(), judgeProperties.getPassword());
            }
            session = conn.openSession();// 打开一个会话
            session.execCommand(cmd);// 执行命令
        } catch (IOException e) {
            log.info("执行命令失败,链接conn:" + conn + ",执行的命令：" + cmd + "  " + e.getMessage());
            return new ExecMessage(e.getMessage(), null);
        }
        ExecMessage result = new ExecMessage();
        try {
            result.setError(message(session.getStderr()));
            result.setStdout(message(session.getStdout()));
            session.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

}
