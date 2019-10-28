package com.yoj.web.util;

import com.yoj.web.cache.EmailCache;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.stereotype.Component;

import java.util.Random;
/**
* @Description:  发送邮件时，注意设置缓存
* @Author: lmz
* @Date: 2019/10/28
*/
@Slf4j
@Component
@CacheConfig(cacheNames = "email")
public class EmailSender {

    @Value("${spring.mail.username}")
    private String from;

    @Autowired
    EmailCache emailCache;
    @Autowired
    JavaMailSenderImpl mailSender;

    /**
     * @Description: 发送注册邮件和验证码
     * @Param: [email]
     * @return: java.lang.String null:发送邮件失败
     * @Author: lmz
     * @Date: 2019/10/20
     */
    public String sendResetPasswordEmail(String email) {
        String checkCode = String.valueOf(new Random().nextInt(899999) + 100000);
        try{
            //发送邮件
            sendEmailMessage(email, "YOJ重置验证码",
                    "您的重置验证码为：" + checkCode);
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
        //设置缓存
        emailCache.setEmailCheckCode(email,checkCode);
        return checkCode;
    }

    /**
     * @Description: 发送注册邮件和验证码
     * @Param: [email]
     * @return: java.lang.String null:发送邮件失败
     * @Author: lmz
     * @Date: 2019/10/20
     */
    public String sendRegisterEmail(String email) {
        //删除缓存
//        emailCache.delEmailCheckCode(email);
        String checkCode = String.valueOf(new Random().nextInt(899999) + 100000);
        try{
            //发送邮件
            sendEmailMessage(email, "YOJ注册验证码",
                    "您的注册验证码为：" + checkCode);
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
        //设置缓存
        emailCache.setEmailCheckCode(email,checkCode);
        return checkCode;
    }


    /**
    * @Description: 发送邮件
    * @Param: [email, subject, Text]
    * @return: void
    * @Author: lmz
    * @Date: 2019/10/20
    */
    private void sendEmailMessage(String email, String subject, String Text) throws Exception {
        SimpleMailMessage message = new SimpleMailMessage();

        message.setSubject(subject);
        message.setText(Text);
        message.setFrom(from);
        message.setTo(email);
        mailSender.send(message);
    }
}
