package com.yoj.nuts.judge.utils.impl;

import com.yoj.nuts.judge.bean.ExecMessage;
import com.yoj.nuts.judge.utils.ExecutorUtil;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import org.springframework.stereotype.Component;

/**
 * @author lmz
 * 	本地系统为linux时使用。
 */
@Component("localExecutor")
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
 
  public String message(InputStream inputStream) {
    BufferedReader reader = null;
    try {
      reader = new BufferedReader(new InputStreamReader(inputStream, "utf-8"));
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
 
