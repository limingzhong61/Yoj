package com.yoj.web.dao;

import com.yoj.web.pojo.Problem;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Mapper
public interface ProblemMapper {


    @Select("SELECT * FROM problem WHERE problem_id = #{pid}")
    Problem getAllById(int pid);

    @Select("SELECT problem_id,user_id,title,tag,description,format_input,format_output," +
            "sample_input,sample_output,hint,time_limit,memory_limit FROM problem WHERE problem_id = #{pid}")
    Problem getProblemViewById(int pid);

    @Select("SELECT * FROM problem")
    List<Problem> getAll();

    Integer insert(Problem problem);
    /**
    * @Description: 动态sql插入，并返回自增主键
    * @Param: [problem]
    * @return: int
    * @Author: lmz
    */
    int insertSelective(Problem problem);

    int updateByPrimaryKey(Problem problem);

    int updateByPrimaryKeySelective(Problem problem);
    /**
    * @Description: 根据problem参数返回问题集合
    * @Param: [problem],注意problem.user_id != null,user_id放回是否解决、提交问题
     * @return: java.util.List<com.yoj.web.pojo.Problem>
    * @Author: lmz
    * @Date: 2019/10/23 
    */ 
    List<Problem> getProblemList(Problem problem);

    /**
    * @Description: delete problem and solution that has problem's pid
    * @Param: [pid]
    * @return: java.lang.Integer
    * @Author: lmz
    * @Date: 2019/12/31
    */
    @Delete("DELETE problem,solution FROM problem LEFT JOIN solution \n" +
            "ON problem.problem_id=solution.problem_id WHERE problem.problem_id= #{pid}")
    Integer deleteProblemById(Integer pid);

    @Select("SELECT problem_id FROM problem WHERE problem_id = #{problemId} LIMIT 1")
    Integer queryById(Integer problemId);

    @Select("SELECT MAX(problem_id) FROM problem")
    Integer getMaxProblemId();
}
