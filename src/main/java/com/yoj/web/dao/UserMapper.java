package com.yoj.web.dao;

import com.yoj.web.bean.User;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

@Repository
@Mapper
public interface UserMapper {

//    @Select("select count(1) from user where user_name=#{userName} and password=#{password}")
//    public int queryUserExist(@Param("userName")String userName,@Param("password")String password);

    @Insert("insert into user(user_name,password,email) values(#{userName},#{password},#{email})")
    @Options(useGeneratedKeys=true, keyProperty="userId", keyColumn="user_id")
    public int insertUser(User user);

    @Select("select * from user where user_name=#{userName} and password=#{password}")
    public User queryUserExist(User user);

    @Select("select count(1) from user where user_name=#{userName}")
    public int queryExistByName(String userName);

    @Select("select count(1) from user where email=#{email}")
    public int queryExistByEmail(String email);

    @Select("select * from user where user_id = #{user_id}")
    public User getUserById(Integer userId);

    @Select("SELECT * FROM `user` WHERE user_name = #{userName}")
    public User getUserByName(String userName);
}
