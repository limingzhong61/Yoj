package com.yoj.web.service;

import com.yoj.web.bean.Solution;
import com.yoj.web.dao.SolutionMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

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
		boolean f = solutionMapper.insetSolution(solution) > 0;
		return f;
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
	
	/**
	 * @description:
	 * @param: @return
	 * @return: List<Solution>
	 * @author nicolas
	 * @date 2019年8月24日
	 */
	public List<Solution> getAllWithUserName() {
		return solutionMapper.getAllWithUserAndProblemName();
	}


    public int countAcceptedByProblemId(Integer pid){
	    return solutionMapper.countAcceptedByProblemId(pid);
    }

    public int countSubmissionByProblemId(Integer pid){
	    return solutionMapper.countSubmissionByProblemId(pid);
    }


	
}
