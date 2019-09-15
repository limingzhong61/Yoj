package com.yoj.web.mapper;

import com.yoj.web.bean.Privilege;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface PrivilegeMapper {
    @Select("SELECT * FROM privilige where user_id = #{userId}")
    public List<Privilege> queryByUserId(Integer userId);
}
