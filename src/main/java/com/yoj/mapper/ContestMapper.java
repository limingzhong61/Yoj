package com.yoj.mapper;

import com.yoj.model.entity.Contest;
import org.apache.ibatis.annotations.*;
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

    @Select("SELECT c.* from contest c,contest_problem cp " +
            "where c.contest_id = cp.contest_id and c.contest_id = #{contestId} and problem_id = #{problemId}")
    Contest getFromContestProblem(@Param("contestId")Integer contestId, @Param("problemId")Integer problemId);
    @Delete("DELETE FROM contest WHERE contest_id = #{contestId}")
    Integer deleteByCid(Integer contestId);
}
