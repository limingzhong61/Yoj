package com.yoj.used;

import org.springframework.stereotype.Service;

@Service("impl1")
public class Implement1 implements MyInterface{
    @Override
    public void className() {
        System.out.println(Implement1.class);
    }

    public static void main(String[] args) {
        new Implement1().className();
    }
}
