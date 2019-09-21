package com.yoj.web.service;

import com.yoj.nuts.judge.bean.static_fianl.Results;
import com.yoj.nuts.judge.utils.ProblemFileUtil;
import com.yoj.web.bean.Problem;
import com.yoj.web.bean.Solution;
import com.yoj.web.dao.ProblemMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
/**
 * @Description: create和update时需要在服务器上创建目录
 * @Author: lmz
 */
public class ProblemService {
    @Autowired
    public ProblemMapper problemMapper;

    /**
     * 更新problem的提交数和通过数
     *
     * @param solution
     * @return
     * @author lmz
     */
    public boolean updateSubmit(Solution solution) {
        if (solution.getResult() == Results.Accepted) {
            return problemMapper.updateAccept() > 0;
        } else {
            return problemMapper.updateSubmit() > 0;
        }
    }

    public Problem queryById(int pid) {
        return problemMapper.queryById(pid);
    }

    public List<Problem> getAll() {
        return problemMapper.getAll();
    }

    public Boolean isSolved(Integer problemId, Integer userId) {
        return problemMapper.isSolved(problemId, userId) > 0;
    }

    public boolean insert(Problem problem) {
        boolean flag = problemMapper.insert(problem) > 0;
        if(flag){
            ProblemFileUtil.createProblemFile(problem);
        }
        return flag;
    }

    public boolean updateByPrimaryKey(Problem problem) {
        boolean flag = problemMapper.updateByPrimaryKey(problem) > 0;
        if(flag){
            ProblemFileUtil.createProblemFile(problem);
        }
        return flag;
    }

    public boolean updateByPrimaryKeySelective(Problem problem){
        return problemMapper.updateByPrimaryKeySelective(problem) > 0;
    }

}
