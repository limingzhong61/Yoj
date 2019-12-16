package com.yoj.web.dao;

import com.yoj.web.pojo.User;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Mapper
public interface UserMapper {

    @Insert("insert into user(user_name,nick_name,password,email,reg_time) " +
            "values(#{userName},#{userName},#{password},#{email},NOW())")
    @Options(useGeneratedKeys = true, keyProperty = "userId", keyColumn = "user_id")
    int insertUser(User user);

    @Select("select * from user where user_name=#{userName} and password=#{password}")
    User getUserExist(User user);

    @Select("select count(1) from user where user_name=#{userName} LIMIT 1")
    int queryExistByName(String userName);

    @Select("select count(1) from user where email=#{email}  LIMIT 1")
    int queryExistByEmail(String email);

    @Select("select * from user where user_id = #{user_id} Limit 1")
    User getUserById(Integer userId);

    @Select("SELECT * FROM `user` WHERE user_name = #{userName}  LIMIT 1")
    User getUserByName(String userName);

    @Select("select * from user where email=#{email}  LIMIT 1")
    User getUserByEmail(String email);

    @Update("UPDATE user SET password = #{password} where email = #{email}")
    int updateUserPasswordByEmail(@Param("password") String password, @Param("email") String email);

    @Update("UPDATE user SET solved = (SELECT COUNT(DISTINCT problem_id) \n" +
            "FROM solution WHERE user_id = #{userId} and result = 0) WHERE user_id = #{userId}")
    Integer updateSolved(Integer userId);

    @Update("UPDATE user SET attempted = (SELECT COUNT(DISTINCT problem_id) \n" +
            "FROM solution WHERE user_id = #{userId}) WHERE user_id = #{userId}")
    Integer updateAttempted(Integer userId);

    /**
    * @Description: by user return userList 
    * @Param: [user]
    * @return: java.util.List<com.yoj.web.pojo.User>
    * @Author: lmz
    * @Date: 2019/10/25 
    */ 
    List<User> getUserList(User user);
}
