package com.yoj.custom.judge.util.impl;

import com.yoj.custom.judge.bean.ExecMessage;
import com.yoj.custom.judge.util.ExecutorUtil;

import java.io.IOException;

/**
 * @author lmz
 * 	本地系统为linux时使用。
 */
public class LocalExecutor implements ExecutorUtil{

  @Override
  public  ExecMessage execute(String cmd) {
    Runtime runtime = Runtime.getRuntime();
    Process exec = null;
    try {
      exec = runtime.exec(cmd);
    } catch (IOException e) {
      e.printStackTrace();
      return new ExecMessage(e.getMessage(), null);
    }
    ExecMessage res = new ExecMessage();
    res.setError(message(exec.getErrorStream()));
    res.setStdout(message(exec.getInputStream()));
    return res;
  }
 
}
 
