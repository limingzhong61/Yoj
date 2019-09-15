package com.yoj.web.service;

import com.yoj.web.bean.Msg;
import com.yoj.web.bean.User;
import com.yoj.web.bean.UserDetailsImpl;
import com.yoj.web.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserService implements UserDetailsService {
    @Autowired
    UserMapper userMapper;

    @Autowired
    PrivilegeService privilegeService;

    /**
    * @Description: 插入用户
    * @Param: [user]
    * @return: Msg
    * @Author: lmz
    * @Date: 2019/8/12
    */
    public Msg insertUser(User user) {
        if(userMapper.queryExistByName(user.getUserName()) > 0){
            return Msg.fail("用户名已存在");
        }
        if(userMapper.insertUser(user) > 0){
            return Msg.success();
        }
        return Msg.fail();
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

    public User getUserById(Integer userId) {
    	return userMapper.getUserById(userId);
    }
    public User getUserByName(String userName){
        return userMapper.getUserByName(userName);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = getUserByName(username);
        if(user == null){
            throw new UsernameNotFoundException("没有该用户");
        }
        return new UserDetailsImpl(user, privilegeService.queryByUserId(user.getUserId()));
    }
}
