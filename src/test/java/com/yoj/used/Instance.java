package com.yoj.used;

import com.yoj.nuts.properties.JudgeProperties;
import org.springframework.beans.factory.annotation.Autowired;

//@Service
public class Instance {
    @Autowired
    JudgeProperties judgeProperties;



    int i = 1;

    Instance(){
        System.out.println(judgeProperties.getIp());
        System.out.println(i);
    }

    public static void main(String[] args) {
        new Instance();
    }
}
