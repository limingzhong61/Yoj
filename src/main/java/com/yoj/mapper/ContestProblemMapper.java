package com.yoj.mapper;

import com.yoj.model.entity.Contest;
import com.yoj.model.entity.ContestProblem;
import com.yoj.model.entity.Problem;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
@Mapper
public interface ContestProblemMapper {

    Integer batchInsert(List<ContestProblem> contestProblemList);

    @Select("SELECT problem.problem_id,title,score FROM problem,contest_problem where contest_id = #{contestId} \n" +
            "and problem.problem_id = contest_problem.problem_id")
    List<Problem> getContestProblemListByContestId(Integer contestId);
    // used by contestMapper.xml
    @Select("SELECT COUNT(*) FROM contest_problem where contest_id = #{contestId}")
    Integer getTotalProblemByContestId(Integer contestId);

    @Select("SELECT COUNT(*) FROM contest_problem where contest_id = #{contestId} and problem_id = #{problem_id}")
    Integer queryHaveContestProblem(Contest contest);

    // used by solutionMapper
    @Select("SELECT score FROM contest_problem WHERE contest_id = #{contestId} " +
            "AND problem_id =#{problemId} LIMIT 1")
    Integer getScore(Map<String, Object> map);

    @Delete("DELETE FROM contest_problem WHERE contest_id = #{contestId}")
    boolean deleteByContestId(Integer contestId);
}
