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
     * @return: java.util.List<com.yoj.web.bean.Problem>
    * @Author: lmz
    * @Date: 2019/10/23 
    */ 
    List<Problem> getProblemList(Problem problem);
}
