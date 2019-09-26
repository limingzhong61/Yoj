package com.yoj.web.service;

import com.yoj.nuts.judge.bean.static_fianl.Results;
import com.yoj.nuts.judge.utils.ProblemFileUtil;
import com.yoj.web.bean.Problem;
import com.yoj.web.bean.Solution;
import com.yoj.web.dao.ProblemMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Description: create和update时需要在服务器上创建目录
 * @Author: lmz
 */
@CacheConfig(cacheNames = "problem")
@Service
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
//    @CachePut
//    public boolean updateSubmit(Solution solution){
//        if (solution.getResult() == Results.Accepted) {
//            return problemMapper.updateAccept(solution.getProblemId()) > 0;
//        } else {
//            return problemMapper.updateSubmit(solution.getProblemId()) > 0;
//        }
//    }
    @CachePut(key = "#result.problemId")
    public Problem updateSubmit(Solution solution){
        Problem problem = problemMapper.queryById(solution.getProblemId());
        problem.setSubmissions(problem.getSubmissions()+1);
        if (solution.getResult() == Results.Accepted) {
            problem.setAccepted(problem.getAccepted()+1);
            if(problemMapper.updateAccept(solution.getProblemId()) > 0){
                return problem;
            }
            return null;
        } else {
            if(problemMapper.updateSubmit(solution.getProblemId()) > 0){
                return problem;
            }
            return null;
        }
    }

    @Cacheable(value = "problem")
    public Problem queryById(int pid) {
        return problemMapper.queryById(pid);
    }
    /**
    * @Description:  @Cacheable,分页数据未缓存
    * @Param: []
    * @return: java.util.List<com.yoj.web.bean.Problem>
    * @Author: lmz
    */
    public List<Problem> getAll() {
        return problemMapper.getAll();
    }
    @Cacheable
    public Boolean isSolved(Integer problemId, Integer userId) {
        return problemMapper.isSolved(problemId, userId) > 0;
    }

    @CachePut(key="#result.problemId")
    public Problem insert(Problem problem) {
        boolean flag = problemMapper.insert(problem) > 0;
        if (flag) {
            ProblemFileUtil.createProblemFile(problem);
            return problem;
        }
        return null;
    }
    @CachePut(key = "#problem.problemId")
    public Problem updateByPrimaryKey(Problem problem) {
        boolean flag = problemMapper.updateByPrimaryKey(problem) > 0;
        if (flag) {
            ProblemFileUtil.createProblemFile(problem);
        }else{
            problem = null;
        }
        return problem;
    }
    @CachePut(key = "#problem.problemId")
    public Problem updateByPrimaryKeySelective(Problem problem) {
        boolean flag = problemMapper.updateByPrimaryKeySelective(problem) > 0;
        if(flag){
            return queryById(problem.getProblemId());
        }
        return null;
    }

}
