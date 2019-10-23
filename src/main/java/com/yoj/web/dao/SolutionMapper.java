package com.yoj.web.dao;

import com.yoj.web.bean.Solution;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Mapper
public interface SolutionMapper {

//    @Insert("insert into solution" +
//            "(problem_id,user_id,user_name,language,code,result,runtime,memory,error_message,submit_time) "
//            + "values(#{problemId},#{userId},#{user_name},#{language},#{code},#{result},#{runtime},#{memory}," +
//            "#{errorMessage},NOW())")
//    @Options(useGeneratedKeys = true, keyProperty = "solutionId", keyColumn = "solution_id")
//    int insetSolution(Solution solution);
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
    Solution queryById(Integer sid);

    /**
     * @Description: 根据输入的solution动态查询问题集合
     * @Param: [solution]
     * @return: java.util.List<com.yoj.web.bean.Solution>
     * @Author: lmz
     * @Date: 2019/10/22
     */
    List<Solution> getListBySelective(Solution solution);

    /**
     * @Description: 根据输入的solution动态查询问题集合
     * @Param: [solution]
     * @return: java.util.List<com.yoj.web.bean.Solution>
     * @Author: lmz
     * @Date: 2019/10/22
     */
    Long countBySelective(Solution solution);

    /**
     * @Description: 根据pid返回提交数
     * @Param: [problemId]
     * @return: java.lang.Integer
     * @Author: lmz
     * @Date: 2019/10/23
     */
    @Select("SELECT COUNT(*) AS submission  FROM solution where problem_id = #{problemId}")
    Integer countSubmissionsByProblemId(Integer problemId);

    @Select("SELECT COUNT(*) AS submission  FROM solution where problem_id = #{problemId} and result = 0")
    int countAcceptedByProblemId(Integer pid);

//    @Select("")
}
