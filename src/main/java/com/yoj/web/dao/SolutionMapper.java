package com.yoj.web.dao;

import com.yoj.web.bean.Solution;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.mapping.FetchType;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Mapper
public interface SolutionMapper {

    @Insert("insert into solution(problem_id,user_id,language,code,result,runtime,memory,error_message,submit_time) "
            + "values(#{problemId},#{userId},#{language},#{code},#{result},#{runtime},#{memory},#{errorMessage},NOW())")
    public int insetSolution(Solution solution);

    /**
     * order by solution_id descend
     *
     * @return
     */
    @Select("SELECT * FROM solution ORDER BY solution_id DESC")
    public List<Solution> getAllByDesc();

    @Select("SELECT * FROM solution ORDER BY solution_id DESC")
    @Results({
            @Result(id = true, column = "solution_id", property = "solutionId"),
            @Result(column = "problem_id", property = "problem", one = @One(select = "com.yoj.web.dao.ProblemMapper.queryProblemTitleAndIdById", fetchType = FetchType.EAGER)),
            @Result(column = "language", property = "language"),
            @Result(column = "code", property = "code"),
            @Result(column = "result", property = "result"),
            @Result(column = "runtime", property = "runtime"),
            @Result(column = "memory", property = "memory"),
            @Result(column = "error_message", property = "errorMessage"),
            @Result(column = "submit_time", property = "submitTime"),
            @Result(column = "user_id", property = "user", one = @One(select = "com.yoj.web.dao.UserMapper.getUserById", fetchType = FetchType.EAGER))
    })
    public List<Solution> getAllWithUserAndProblemName();

    @Select("select count(1) from solution where problem_id = #{pid} and result  = 0")
    int countAcceptedByProblemId(Integer pid);

    @Select("select count(1) from solution where problem_id = #{pid}")
    int countSubmissionByProblemId(Integer pid);

    @Select("select * from solution where problem_id = #{pid}")
    Solution queryById(Integer sid);
}
