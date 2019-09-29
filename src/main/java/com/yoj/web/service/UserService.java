package com.yoj.web.service;

import com.yoj.web.bean.util.Msg;
import com.yoj.web.bean.User;
import com.yoj.web.bean.util.UserDetailsImpl;
import com.yoj.web.dao.UserMapper;
import org.jasypt.encryption.StringEncryptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

/**
 * @Description: insert/update时请对密码进行加密
 * @Author: lmz
 * @Date: 2019/9/22
 */
@CacheConfig(cacheNames="user")
@Service
public class UserService implements UserDetailsService {
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private PrivilegeService privilegeService;
    @Autowired
    private StringEncryptor encryptor;

    /**
     * @Description: 插入用户 返回对象不统一。。。。未使用缓存
     * @Param: [user]
     * @return: Msg
     * @Author: lmz
     * @Date: 2019/8/12
     */
//    @CachePut(key = "#result.userId")
    public Msg insertUser(User user) {
        if (userMapper.queryExistByName(user.getUserName()) > 0) {
            return Msg.fail("用户名已存在");
        }
        user.setPassword(encryptor.encrypt(user.getPassword()));
        if (insertUserUseCache(user) != null) {
            return Msg.success();
        }
        return Msg.fail();
    }
    @CachePut(key = "#result.userId")
    public User insertUserUseCache(User user) {
        user.setPassword(encryptor.encrypt(user.getPassword()));
        if (userMapper.insertUser(user) > 0) {
            return user;
        }
        return null;
    }


    /**
     * @Description: 判断用戶名和密码是否正确
     * @Param: [username, password]
     * @return: boolean
     * @Author: lmz
     * @Date: 2019/8/11
     */
    @Cacheable
    public User queryUserExist(User user) {
        return userMapper.queryUserExist(user);
    }
    @Cacheable
    public User getUserById(Integer userId) {
        return userMapper.getUserById(userId);
    }
    @Cacheable(unless = "#result == null")
    public User getUserByName(String userName) {
        return userMapper.getUserByName(userName);
    }

//    @Cacheable 不能缓存，插入之后，就已经变了
    public boolean queryExistByEmail(String email){
        return userMapper.queryExistByEmail(email) > 0;
    }

//    @Cacheable
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = getUserByName(username);
        if (user == null) {
            throw new UsernameNotFoundException("没有该用户");
        }
        user.setPassword(encryptor.decrypt(user.getPassword()));
        return new UserDetailsImpl(user, privilegeService.queryByUserId(user.getUserId()));
    }
}
