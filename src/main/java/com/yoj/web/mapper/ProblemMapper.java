package com.yoj.web.mapper;

import com.yoj.web.bean.Problem;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Mapper
public interface ProblemMapper {

    /**
     * 修改提交数,此提交未通过
     *
     * @param solution
     * @return
     * @author lmz
     */
    @Update("update problem set submissions=submissions+1 where problem_id = 1")
    public int updateSubmit();

    /**
     * 修改提交数,此提交通过
     *
     * @return
     * @author lmz
     */
    @Update("update problem set submissions=submissions+1,accepted = accepted+1 where problem_id = 1")
    public int updateAccept();

    @Select("SELECT * FROM problem WHERE problem_id = #{pid}")
    public Problem queryById(int pid);

    @Select("SELECT * FROM problem")
    public List<Problem> getAll();

    @Select("SELECT count(1) FROM solution WHERE problem_id = #{pid} and user_id = #{uid} and result = 0")
    public int isSolved(@Param("pid") Integer problemId, @Param("uid") Integer userId);

    @Insert("INSERT INTO problem(user_id,title,tag,description," +
            "format_input,format_output,sample_input,sample_output,hint,time_limit,memory_limit) " +
            "VALUES(#{userId},#{title},#{tag},#{description},#{formatInput},#{formatOutput}," +
            "#{sampleInput},#{sampleOutput},#{hint},#{timeLimit},#{memoryLimit})")
    int insert(Problem problem);
}
