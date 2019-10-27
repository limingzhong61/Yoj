package com.yoj.nuts.judge.util;

import com.yoj.nuts.judge.bean.ExecMessage;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

//@Component
public interface ExecutorUtil {
    //默认编码UTF-8
    public static String DEFAULT_CHART = "UTF-8";

    public ExecMessage execute(String cmd);

    default public String message(InputStream inputStream) {
        BufferedReader reader = null;
        try {
            reader = new BufferedReader(new InputStreamReader(inputStream, DEFAULT_CHART));
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
