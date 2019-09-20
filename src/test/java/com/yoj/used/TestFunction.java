package com.yoj.used;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class TestFunction {
    public static void main(String[] args) {
//        Properties prop = new Properties();
        try {
            InputStream in = TestFunction.class.getClassLoader().getResourceAsStream("judge.properties");
            Properties prop = new Properties();
            prop.load(in);
            prop.getProperty("path");
//            prop.load(new FileInputStream("E:/tmp/judge.properties"));
//            prop.load(new FileInputStream("/judge.properties"));
//            prop.load(PropertiesMain.class.getClassLoader().getResourceAsStream("judge.properties"));
            System.out.println(prop.getProperty("path"));
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
