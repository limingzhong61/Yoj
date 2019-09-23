package com.yoj.web.dao;

import com.yoj.web.bean.Problem;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Mapper
public interface ProblemMapper {

    /**
     * 修改提交数,此提交未通过
     * @param solution
     * @return
     * @author lmz
     */
    @Update("update problem set submissions=submissions+1 where problem_id = #{pid}")
    int updateSubmit(Integer pid);

    /**
     * 修改提交数,此提交通过
     * @return
     * @author lmz
     */
    @Update("update problem set submissions=submissions+1,accepted = accepted+1 where problem_id = #{pid}")
    int updateAccept(Integer pid);

    @Select("SELECT * FROM problem WHERE problem_id = #{pid}")
    Problem queryById(int pid);

    @Select("SELECT * FROM problem")
    List<Problem> getAll();

    @Select("SELECT count(1) FROM solution WHERE problem_id = #{pid} and user_id = #{uid} and result = 0")
    int isSolved(@Param("pid") Integer problemId, @Param("uid") Integer userId);

    int insert(Problem problem);
    /**
    * @Description: 动态sql插入，并返回自增主键
    * @Param: [problem]
    * @return: int
    * @Author: lmz
    */
    int insertSelective(Problem problem);

    int updateByPrimaryKey(Problem problem);

    int updateByPrimaryKeySelective(Problem problem);
    @Select("SELECT problem_id,title FROM problem WHERE problem_id = #{pid}")
    Problem queryProblemTitleAndIdById(Integer pid);
}
