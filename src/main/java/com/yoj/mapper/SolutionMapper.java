package com.yoj.mapper;

import com.yoj.model.entity.Solution;
import com.yoj.model.entity.User;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
@Mapper
public interface SolutionMapper {

    int insertSelective(Solution solution);

    /**
     * order by solution_id descend
     *
     * @return
     */
    @Select("SELECT * FROM solution ORDER BY solution_id DESC")
    List<Solution> getAllByDesc();

    @Select("select count(1) from solution where problem_id = #{pid}")
    int countSubmissionByProblemId(Integer pid);

    @Select("select * from solution where solution_id = #{pid}")
    Solution getById(Integer sid);

    /**
     * @Description: 根据输入的solution动态查询问题集合
     * @Param: [solution]
     * @return: java.utils.List<com.yoj.model.entity.Solution>
     * @Author: lmz
     * @Date: 2019/10/22
     */
    List<Solution> getListBySelective(Solution solution);

    /**
     * @Description: 根据输入的solution动态查询问题集合
     * @Param: [solution]
     * @return: java.utils.List<com.yoj.model.entity.Solution>
     * @Author: lmz
     * @Date: 2019/10/22
     */
    Long countBySelective(Solution solution);


    @Select("SELECT COUNT(*) FROM solution where user_id = #{userId} and result = 0")
    int countAcceptedByUserId(Integer userId);
    @Select("SELECT COUNT(*) FROM solution where user_id = #{userId}")
    Integer countSubmissionsByUserId(Integer userId);
    // use by userMapper getUserList
    @Select("SELECT COUNT(DISTINCT problem_id) FROM solution where user_id = #{userId}")
    int countAttemptedByUserId(Integer userId);
    @Select("SELECT COUNT(DISTINCT problem_id) FROM solution where user_id = #{userId}  and result = 0")
    Integer countSolvedByUserId(Integer userId);

    @Select("SELECT COUNT(*) AS submission  FROM solution where problem_id = #{problemId}")
    Integer countSubmissionsByProblemId(Integer problemId);
    @Select("SELECT COUNT(*) FROM solution where problem_id = #{problemId} and result = 0")
    int countAcceptedByProblemId(Integer pid);


    @Select("SELECT solution_id FROM solution WHERE problem_id = #{problemId} and user_id = #{userId} and result = 0 LIMIT 1")
    Integer querySolved(Map<String, Object> map);

    @Select("SELECT solution_id FROM solution WHERE problem_id = #{problemId} and user_id = #{userId} LIMIT 1")
    Integer querySubmitted(Map<String, Object> map);

    @Update("UPDATE solution set result = #{result},runtime = #{runtime},memory = #{memory},error_message = #{errorMessage}," +
            "test_result = #{testResult} where solution_id = #{solutionId}")
    Integer updateById(Solution updateSolution);

    @Select("SELECT solution_id,problem_id,result,submit_time FROM solution where contest_id =#{contestId}" +
            " and user_id = #{userId}")
    List<Solution> getUserContestRecord(@Param("contestId") Integer contestId,@Param("userId") Integer userId);

    List<Solution> getByContestId(Integer contestId);

    @Delete("DELETE FROM  solution  WHERE problem_id= #{pid}")
    Integer deleteByProblemId(Integer pid);

    @Update("UPDATE solution SET result = 10,runtime = NULL,memory = NULL,error_message = NULL," +
            "test_result = NULL WHERE problem_id =  #{problemId}")
    Integer updateByProblemId(Integer problemId);
    List<User> getContestRankList(Integer cid);
}
