package com.yoj.web.service;

import com.yoj.web.bean.Solution;
import com.yoj.web.mapper.SolutionMapper;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SolutionService {
	@Autowired
	public SolutionMapper solutionMapper;

	/**
	 * @description: insert solution
	 * @param: @param solution
	 * @param: @return
	 * @return: boolean
	 * @author nicolas
	 * @date 2019年8月25日
	 */
	public boolean insertSolution(Solution solution) {
		return solutionMapper.insetSolution(solution) > 0;
	}

	/**
	 * @description:
	 * @param: @return
	 * @return: List<Solution>
	 * @author nicolas
	 * @date 2019年8月24日
	 */
	public List<Solution> getAllByDesc() {
		return solutionMapper.getAllByDesc();
	}
}
