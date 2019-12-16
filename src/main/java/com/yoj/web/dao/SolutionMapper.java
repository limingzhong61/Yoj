package com.yoj.web.dao;

import com.yoj.web.pojo.Solution;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
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
     * @return: java.util.List<com.yoj.web.pojo.Solution>
     * @Author: lmz
     * @Date: 2019/10/22
     */
    List<Solution> getListBySelective(Solution solution);

    /**
     * @Description: 根据输入的solution动态查询问题集合
     * @Param: [solution]
     * @return: java.util.List<com.yoj.web.pojo.Solution>
     * @Author: lmz
     * @Date: 2019/10/22
     */
    Long countBySelective(Solution solution);

    @Select("SELECT COUNT(*) AS submission  FROM solution where problem_id = #{problemId}")
    Integer countSubmissionsByProblemId(Integer problemId);

    @Select("SELECT COUNT(*) AS submission  FROM solution where user_id = #{userId} and result = 0")
    int countAcceptedByUserId(Integer userId);


    @Select("SELECT COUNT(*) AS submission  FROM solution where user_id = #{userId}")
    Integer countSubmissionsByUserId(Integer userId);


    @Select("SELECT COUNT(*) AS submission  FROM solution where problem_id = #{problemId} and result = 0")
    int countAcceptedByProblemId(Integer pid);

    @Select("SELECT solution_id FROM solution WHERE problem_id = #{problemId} and user_id = #{userId} and result = 0 LIMIT 1")
    Integer querySolved(Map<String, Object> map);

    @Select("SELECT solution_id FROM solution WHERE problem_id = #{problemId} and user_id = #{userId} LIMIT 1")
    Integer querySubmitted(Map<String, Object> map);

    Integer updateByPrimaryKey(Solution solution);
}
