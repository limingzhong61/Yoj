package com.yoj.web.mapper;

import com.yoj.web.bean.Solution;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

@Repository
@Mapper
public interface SolutionMapper {

    @Insert("insert into solution(problem_id,user_id,language,code,result) values(#{problemId},#{userId},#{language},#{code},#{result})")
    public int insetSolution(Solution solution);
}
