package com.yoj.web.service;

import com.yoj.nuts.judge.util.ProblemFileUtil;
import com.yoj.web.bean.Problem;
import com.yoj.web.dao.ProblemMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
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
    private ProblemMapper problemMapper;

    @Autowired
    @Qualifier("localProblemFileUtil")
    private ProblemFileUtil problemFileUtil;

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
    /**
    * @Description: 判断是否用户提交、通过此题
    * @Param: [problemId, userId]
    * @return: int 1 accepted, -1 submitted, 0 not submitted
    * @Author: lmz
    * @Date: 2019/10/23 
    */ 
    public int getUserState(Integer problemId, Integer userId) {
        if(problemMapper.isSolved(problemId, userId) != null){
            return 1;
        }
        if(problemMapper.isSubmitted(problemId, userId) != null){
            return -1;
        }
        return 0;
    }

    @CachePut(key="#result.problemId")
    public Problem insert(Problem problem) {
        boolean flag = problemMapper.insert(problem) > 0;
        if (flag) {
            problemFileUtil.createProblemFile(problem);
            return problem;
        }
        return null;
    }
    @CachePut(key = "#problem.problemId")
    public Problem updateByPrimaryKey(Problem problem) {
        boolean flag = problemMapper.updateByPrimaryKey(problem) > 0;
        if (flag) {
            problemFileUtil.createProblemFile(problem);
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

    /**
     * @Description: 放回问题集合
     * @Param: []
     * @return: java.util.List<com.yoj.web.bean.Problem>
     * @Author: lmz
     * @Date: 2019/10/23
     */
    public List<Problem> getProblemList(){
        return problemMapper.getProblemList();
    }

}
