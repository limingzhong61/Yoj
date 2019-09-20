package com.yoj.nuts.judge.utils;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class PropertiesUtil {
    private static Properties prop;

    static {
        InputStream in = PropertiesUtil.class.getClassLoader().getResourceAsStream("judge.properties");
        prop = new Properties();
        try {
            prop.load(in);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static String get(String key){
        return prop.getProperty(key);
    }

    public static void main(String[] args) {
        System.out.println(get("ip"));
    }
}
