package com.yoj.web.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Repository;

import com.yoj.web.bean.Problem;

import java.util.List;

@Repository
@Mapper
public interface ProblemMapper {

	/**
	 * 修改提交数,此提交未通过
	 *@author lmz
	 * @param solution
	 * @return
	 */
	@Update("update problem set submissions=submissions+1 where problem_id = 1")
	public int updateSubmit();
	
	/**
	 * 修改提交数,此提交通过
	 *@author lmz
	 * @param solution
	 * @return
	 */
	@Update("update problem set submissions=submissions+1,accepted = accepted+1 where problem_id = 1")
	public int updateAccept();

	@Select("SELECT * FROM problem WHERE problem_id = #{pid}")
	public Problem queryById(int pid);

	@Select("SELECT * FROM problem")
	public List<Problem> getAll();
}
