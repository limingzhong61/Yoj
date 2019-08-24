package com.yoj.web.service;

import com.yoj.web.bean.User;
import com.yoj.web.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    @Autowired
    UserMapper userMapper;
//    /**
//    * @Description: 判断用戶名和密码是否正确
//    * @Param: [username, password]
//    * @return: boolean
//    * @Author: lmz
//    * @Date: 2019/8/11
//    */
//    public boolean queryUserExist(String userName,String password){
//        return userMapper.queryUserExist(userName,password) > 0;
//    };
    /**
    * @Description: 插入用户
    * @Param: [user]
    * @return: boolean
    * @Author: lmz
    * @Date: 2019/8/12
    */
    public boolean insertUser(User user) {
        return userMapper.insertUser(user) > 0;
    }

    /**
     * @Description: 判断用戶名和密码是否正确
     * @Param: [username, password]
     * @return: boolean
     * @Author: lmz
     * @Date: 2019/8/11
     */
    public User queryUserExist(User user) {
        return userMapper.queryUserExist(user);
    }
}
