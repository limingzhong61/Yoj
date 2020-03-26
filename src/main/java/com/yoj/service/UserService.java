package com.yoj.service;

import com.yoj.utils.cache.UserCacheUtil;
import com.yoj.mapper.SolutionMapper;
import com.yoj.mapper.UserMapper;
import com.yoj.model.entity.User;
import com.yoj.model.vo.Msg;
import com.yoj.model.pojo.util.UserDetailsImpl;
import lombok.extern.slf4j.Slf4j;
import org.jasypt.encryption.StringEncryptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.scheduling.annotation.Async;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Description: insert/update时请对密码进行加密
 * @Author: lmz
 * @Date: 2019/9/22
 */
@Slf4j
@CacheConfig(cacheNames = "user")
@Service
public class UserService implements UserDetailsService {
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private StringEncryptor encryptor;
    @Autowired
    private UserCacheUtil userCache;
    @Autowired
    private SolutionMapper solutionMapper;

    @CachePut(key = "#result.userId", unless = "#result == null")
    public User updateUserPasswordByEmail(User user) {
        user.setPassword(encryptor.encrypt(user.getPassword()));
        if (userMapper.updateUserPasswordByEmail(user.getPassword(), user.getEmail()) > 0) {
            return userMapper.getUserByEmail(user.getEmail());
        }
        return null;
    }

    /**
     * @Description: 插入用户 返回对象不统一。。。。未使用缓存
     * @Param: [user]
     * @return: Msg
     * @Author: lmz
     * @Date: 2019/8/12
     */
//    @CachePut(key = "#result.userId")
    public Msg insertUser(User user) {
        if (userMapper.queryExistByName(user.getUsername()) > 0) {
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
            //cache no have nick name
            user.setNickName(user.getUsername());
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
        return userMapper.getUserExist(user);
    }

    @Cacheable
    public User getUserById(Integer userId) {
        return userMapper.getUserById(userId);
    }

    @Cacheable(unless = "#result == null")
    public User getUserByName(String username) {
        return userMapper.getUserByName(username);
    }

    public boolean queryExistByEmail(String email) {
        return userMapper.queryExistByEmail(email) > 0;
    }

    @Cacheable(unless = "#result == null")
    public User getUserByEmail(String email) {
        return userMapper.getUserByEmail(email);
    }

    //    @Cacheable
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = getUserByName(username);
        if (user == null) {
            throw new UsernameNotFoundException("没有该用户");
        }
        user.setPassword(encryptor.decrypt(user.getPassword()));
        return new UserDetailsImpl(user);
    }

    @Cacheable(unless = "#result == null")
    public User getUseById(Integer userId) {
        return userMapper.getUserById(userId);
    }


    public List<User> getUserList(User user) {
        return userMapper.getUserList(user);
    }

    @CachePut(key = "#result.userId", unless = "#result == null")
    public User updateUserInfoById(User user) {
        userMapper.updateUserInfoById(user);
        return userMapper.getUserById(user.getUserId());
    }

    /**
     * the strategy of update score is 10 multiple counts whose user soled problems
     * @param userId
     */
    @Async
    public void updateScoreById(Integer userId) {
        Integer solvedProblem = solutionMapper.countSolvedByUserId(userId);
        userMapper.updateScoreById(solvedProblem*10,userId);
    }
}
