package com.yoj.web.service;

import com.yoj.custom.judge.util.ProblemFileUtil;
import com.yoj.web.pojo.Problem;
import com.yoj.web.dao.ProblemMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Description: create和update时 need update mapping files
 * @Author: lmz
 */
@CacheConfig(cacheNames = "problem")
@Service
public class ProblemService {
    @Autowired
    private ProblemMapper problemMapper;

    @Autowired
    private ProblemFileUtil problemFileUtil;

    @Cacheable(key = "#pid",unless = "#result == null")
    public Problem getViewInfoById(int pid) {
        return problemMapper.getProblemViewById(pid);
    }

    /**
     * @Description: @Cacheable,分页数据未缓存
     * @Param: []
     * @return: java.util.List<com.yoj.web.pojo.Problem>
     * @Author: lmz
     */
    public List<Problem> getAll() {
        return problemMapper.getAll();
    }

    public Problem getAllById(int pid) {
        return problemMapper.getAllById(pid);
    }

    /**
     * Insert problem with max problemId + 1 ensure problem id is  serial
     * There are just only a few  users have add problem authority
     * Not have big chance to meet same pid at the same time,
     * and also have primary key validate
     *
     * @param problem
     * @return
     */
    @CachePut(key = "#result.problemId")
    public Problem insert(Problem problem) {
        Integer maxProblemId = problemMapper.getMaxProblemId();
        problem.setProblemId(maxProblemId + 1);
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
        } else {
            problem = null;
        }
        return problem;
    }

    @CachePut(key = "#problem.problemId")
    public Problem updateByPrimaryKeySelective(Problem problem) {
        boolean flag = problemMapper.updateByPrimaryKeySelective(problem) > 0;
        if (flag) {
            return getViewInfoById(problem.getProblemId());
        }
        return null;
    }

    /**
     * @Description: 根据problem参数返回问题集合
     * @Param: [problem], 注意problem.user_id != null,by user_id return 是否解决、提交问题
     * @return: java.util.List<com.yoj.web.pojo.Problem>
     * @Author: lmz
     * @Date: 2019/10/23
     */
    public List<Problem> getProblemList(Problem problem) {
        return problemMapper.getProblemList(problem);
    }

    public boolean deleteProblemById(Integer pid) {
        return problemMapper.deleteProblemById(pid) > 0;
    }

    public boolean queryById(Integer problemId) {
        return problemMapper.queryById(problemId) != null;
    }
}
