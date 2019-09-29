package com.yoj.nuts.judge.util;

import java.io.InputStream;

import com.yoj.nuts.judge.bean.ExecMessage;

public interface ExecutorUtil {
	
	public ExecMessage execute(String cmd);
	 
	public String message(InputStream inputStream);
}
