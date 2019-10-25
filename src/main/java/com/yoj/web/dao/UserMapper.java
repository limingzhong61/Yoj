package com.yoj.web.dao;

import com.yoj.web.bean.User;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Mapper
public interface UserMapper {

    @Insert("insert into user(user_name,password,email) values(#{userName},#{password},#{email})")
    @Options(useGeneratedKeys = true, keyProperty = "userId", keyColumn = "user_id")
    int insertUser(User user);

    @Select("select * from user where user_name=#{userName} and password=#{password}")
    User getUserExist(User user);

    @Select("select count(1) from user where user_name=#{userName}")
    int queryExistByName(String userName);

    @Select("select count(1) from user where email=#{email}")
    int queryExistByEmail(String email);

    @Select("select * from user where user_id = #{user_id} Limit 1")
    User getUserById(Integer userId);

    @Select("SELECT * FROM `user` WHERE user_name = #{userName}")
    User getUserByName(String userName);

    @Select("select * from user where email=#{email}")
    User getUserByEmail(String email);

    @Update("UPDATE user SET password = #{password} where email = #{email}")
    int updateUserPasswordByEmail(@Param("password") String password, @Param("email") String email);

    /**
    * @Description: by user return userList 
    * @Param: [user]
    * @return: java.util.List<com.yoj.web.bean.User> 
    * @Author: lmz
    * @Date: 2019/10/25 
    */ 
    List<User> getUserList(User user);
}
