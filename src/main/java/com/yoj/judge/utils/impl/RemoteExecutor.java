package com.yoj.judge.utils.impl;

import ch.ethz.ssh2.Connection;
import ch.ethz.ssh2.Session;
import com.yoj.judge.bean.ExecMessage;
import com.yoj.judge.utils.ExecutorUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * @author lmz 远程连接linux使用
 */
@Component("remoteExcutor")
public class RemoteExecutor implements ExecutorUtil {

	private static final Logger log = LoggerFactory.getLogger(RemoteExecutor.class);
	//默认编码UTF-8
	private static String DEFAULTCHART = "UTF-8";
	private static Connection conn;

	public RemoteExecutor() {
		login("47.103.195.173", "nicolas", "nicolas");
	}
	
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
			if (conn != null) {
				session = conn.openSession();// 打开一个会话
				session.execCommand(cmd);// 执行命令
			}
		} catch (IOException e) {
			log.info("执行命令失败,链接conn:" + conn + ",执行的命令：" + cmd + "  " + e.getMessage());
			return new ExecMessage(e.getMessage(), null);
		}

		ExecMessage result = new ExecMessage();
		result.setError(message(session.getStderr()));
		result.setStdout(message(session.getStdout()));
		session.close();
		return result;
	}
	
	@Override
	public String message(InputStream inputStream) {
		BufferedReader reader = null;
		try {
			reader = new BufferedReader(new InputStreamReader(inputStream, DEFAULTCHART));
			StringBuilder message = new StringBuilder();
			String str;
			while ((str = reader.readLine()) != null) {
				message.append(str);
			}
			String result = message.toString();
			if (result.equals("")) {
				return null;
			}
			return result;
		} catch (IOException e) {
			return e.getMessage();
		} finally {
			try {
				inputStream.close();
				reader.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

}
