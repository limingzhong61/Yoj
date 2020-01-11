package com.yoj.web.dao;

import com.yoj.web.pojo.Contest;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Mapper
public interface ContestMapper {

    Integer insert(Contest contest);
    @Select("SELECT * from contest where contest_id = #{contestId}")
    Contest getById(Integer contestId);

    List<Contest> getList(Contest contest);

    @Update("UPDATE contest SET title = #{title},start_time=#{startTime},end_time=#{endTime}," +
            "description=#{description} where contest_id = #{contestId}")
    Integer updateById(Contest contest);
}
