package com.yoj.mapper;

import com.yoj.model.entity.User;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Mapper
public interface UserMapper {

    @Insert("insert into user(username,nick_name,password,email,reg_time,role) " +
            "values(#{username},#{username},#{password},#{email},NOW(),#{role})")
    @Options(useGeneratedKeys = true, keyProperty = "userId", keyColumn = "user_id")
    int insertUser(User user);

    @Select("select * from user where username=#{username} and password=#{password}")
    User getUserExist(User user);

    @Select("select count(1) from user where username=#{username} LIMIT 1")
    int queryExistByName(String username);

    @Select("select count(1) from user where email=#{email}  LIMIT 1")
    int queryExistByEmail(String email);

    @Select("select user_id,username,nick_name,role,intro,email,reg_time from user where user_id = #{user_id} Limit 1")
    User getUserById(Integer userId);

    @Select("SELECT * FROM `user` WHERE username = #{username}  LIMIT 1")
    User getUserByName(String username);

    @Select("select * from user where email=#{email}  LIMIT 1")
    User getUserByEmail(String email);

    @Update("UPDATE user SET password = #{password} where email = #{email}")
    int updateUserPasswordByEmail(@Param("password") String password, @Param("email") String email);


    /**
    * @Description: by user return userList 
    * @Param: [user]
    * @return: java.utils.List<com.yoj.model.entity.User>
    * @Author: lmz
    * @Date: 2019/10/25 
    */ 
    List<User> getUserList(User user);
    @Update("UPDATE user set username = #{username}, nick_name = #{nickName},intro = #{intro} where user_id = #{userId}")
    Integer updateUserInfoById(User user);

    // used by solutionMapper
    @Select("SELECT nick_name FROM user WHERE user_id = #{userId}")
    String getNickNameById(Integer userId);

    @Update("UPDATE `user` SET score = #{score} WHERE user_id = #{userId}")
    void updateScoreById(@Param("score") Integer score,@Param("userId") Integer userId);
}
