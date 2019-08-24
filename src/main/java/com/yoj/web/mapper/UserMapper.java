package com.yoj.web.mapper;

import com.yoj.web.bean.User;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

@Repository
@Mapper
public interface UserMapper {

//    @Select("select count(1) from user where user_name=#{userName} and password=#{password}")
//    public int queryUserExist(@Param("userName")String userName,@Param("password")String password);

    @Insert("insert into user(user_name,password,email) values(#{userName},#{password},#{email})")
    public int insertUser(User user);

    @Select("select * from user where user_name=#{userName} and password=#{password}")
    public User queryUserExist(User user);
}
