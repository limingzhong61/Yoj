package com.yoj.web.mapper;

import com.yoj.web.bean.Solution;

import java.util.List;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

@Repository
@Mapper
public interface SolutionMapper {

    @Insert("insert into solution(problem_id,user_id,language,code,result,time,memory,error_message) "
    		+ "values(#{problemId},#{userId},#{language},#{code},#{result},#{time},#{memory},#{errorMessage})")
    public int insetSolution(Solution solution);

    /**
     * order by solution_id descend
     * @return
     */
    @Select("SELECT * FROM solution ORDER BY solution_id DESC")
	public List<Solution> getAllByDesc();
}
