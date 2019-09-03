package com.yoj.web.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yoj.web.bean.Problem;
import com.yoj.web.bean.Solution;
import com.yoj.web.bean.static_fianl.JudgeResult;
import com.yoj.web.mapper.ProblemMapper;

import java.util.List;

@Service
public class ProblemService {
	@Autowired
	public ProblemMapper problemMapper;
	
	/**
	 * 更新problem的提交数和通过数
	 *@author lmz
	 * @param solution
	 * @return
	 */
	public boolean updateSubmit(Solution solution) {
		if(JudgeResult.ACCEPT.equals(solution.getResult())) {
			return problemMapper.updateAccept() > 0;
		}else {
			return problemMapper.updateSubmit() > 0;
		}
	}

	public Problem queryById(int pid) {
		return problemMapper.queryById(pid);
	}

    public List<Problem> getAll() {
		return problemMapper.getAll();
    }
}
