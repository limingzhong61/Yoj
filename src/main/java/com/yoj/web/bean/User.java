package com.yoj.web.bean;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class User {

    Integer userId;

    String userName;

    String password;

    String email;
}
