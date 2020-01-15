package com.yoj.web.pojo;

import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Email;
import javax.validation.constraints.Pattern;
import java.util.Date;

@Data
public class User {

    private Integer userId;
    // 用户名最长，20个字节
    @Length(min = 1, max = 20, message = "用户名长度错误")
    @Pattern(regexp = "\\w+", message = "用户名只能包含字母、数字、下划线")
    private String username;

    // 昵称最长，20个字节
    @Length(min = 1, max = 20, message = "昵称长度错误")
    private String nickName;

    // 简介：introduction，
    @Length(max = 255, message = "个人简介过长")
    private String intro;

    @Length(min = 6, message = "密码长度过短")
    private String password;

    @Email(message = "请输入一个有效的电子邮件地址")
    private String email;

    private Date regTime;
    // 获取积分，
    private Integer score;

    //user roles,can be multiple
    private String role;

    //非表中字段
    private String emailCode;
    private String imageCode;
    // problem
    private Integer attempted;
    private Integer solved;
    // count solution
    private Integer accepted;
    private Integer submissions;
    // spring security remember-me
    private boolean rememberMe;
    //contest problem
    private Integer totalRunTime;
}
