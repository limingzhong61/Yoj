package com.yoj.web.dao;

import com.yoj.web.bean.Problem;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Mapper
public interface ProblemMapper {


    @Select("SELECT * FROM problem WHERE problem_id = #{pid}")
    Problem queryById(int pid);

    @Select("SELECT * FROM problem")
    List<Problem> getAll();

    @Select("SELECT solution_id FROM solution WHERE problem_id = #{pid} and user_id = #{uid} and result = 0 LIMIT 1")
    Integer isSolved(@Param("pid") Integer problemId, @Param("uid") Integer userId);

    @Select("SELECT solution_id FROM solution WHERE problem_id = #{pid} and user_id = #{uid} LIMIT 1")
    Integer isSubmitted(@Param("pid") Integer problemId, @Param("uid") Integer userId);

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
    * @Description: 放回问题集合
    * @Param: [] 
    * @return: java.util.List<com.yoj.web.bean.Problem> 
    * @Author: lmz
    * @Date: 2019/10/23 
    */ 
    List<Problem> getProblemList();
}
