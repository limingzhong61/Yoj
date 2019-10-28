package com.yoj.web.bean;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Date;

@Getter
@Setter
@ToString
public class User {

    Integer userId;

    String userName;

    String password;

    String email;

    Date regTime;

    Integer attempted;

    Integer solved;

    //非表中字段
    String emailCode;
    String imageCode;

    Integer accepted;
    Integer submissions;

}
