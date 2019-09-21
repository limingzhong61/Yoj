package com.yoj.used;

import org.springframework.stereotype.Service;

@Service("impl2")
public class Implement2 implements MyInterface {
    @Override
    public void className() {
        System.out.println(Implement2.class);
    }
}
