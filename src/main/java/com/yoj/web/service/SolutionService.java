package com.yoj.web.service;

import com.yoj.web.bean.Solution;
import com.yoj.web.dao.SolutionMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;

@CacheConfig(cacheNames = "solution")
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
	@CachePut(key = "#result.solutionId")
	public Solution insertSolution(Solution solution) {
		if(solutionMapper.insetSolution(solution) > 0){
			return solution;
        }
		return null;
	}

    @Cacheable
    public Solution queryById(Integer sid){
	    return solutionMapper.queryById(sid);
    }

	/**
	 * @description: @Cacheable 分页数据未缓存
	 * @param: @return
	 * @return: List<Solution>
	 * @author nicolas
	 * @date 2019年8月24日
	 */
	public List<Solution> getAllByDesc() {
		return solutionMapper.getAllByDesc();
	}
	
	/**
	 * @description: @Cacheable 分页数据未缓存
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
