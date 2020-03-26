package com.yoj.service;

import com.yoj.mapper.ProblemMapper;
import com.yoj.mapper.SolutionMapper;
import com.yoj.model.dto.JudgeCase;
import com.yoj.model.entity.Problem;
import com.yoj.utils.file.ProblemFileUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @Description: create和update时 need update mapping files
 * @Author: lmz
 */
@CacheConfig(cacheNames = "problem")
@Service
@Slf4j
public class ProblemService {
    @Autowired
    private ProblemMapper problemMapper;
    @Autowired
    private SolutionMapper solutionMapper;
    @Autowired
    private ProblemFileUtil problemFileUtil;

    @Cacheable(key = "#pid", unless = "#result == null")
    public Problem getViewInfoById(int pid) {
        return problemMapper.getProblemViewById(pid);
    }

    /**
     * @Description: @Cacheable,分页数据未缓存
     * @Param: []
     * @return: java.utils.List<com.yoj.model.entity.Problem>
     * @Author: lmz
     */
    public List<Problem> getAll() {
        return problemMapper.getAll();
    }

    public Problem getAllById(int pid) {
        Problem problem = problemMapper.getAllById(pid);
        List<JudgeCase> judgeData = problemFileUtil.getJudgeData(problem.getProblemId());
        if (judgeData == null) {
            return null;
        }
        problem.setJudgeData(judgeData);
        return problem;
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
    @CachePut(key = "#result.problemId", unless = "#result == null")
    public Problem insert(Problem problem) {
        Integer maxProblemId = problemMapper.getMaxProblemId();
        problem.setProblemId(maxProblemId + 1);
        boolean flag = problemMapper.insert(problem) > 0;
        if (!flag) {
            return null;
        }
        // 创建文件失败
        if (!problemFileUtil.createProblemFile(problem)) {
            problemMapper.deleteProblemById(problem.getProblemId());
            log.info("create file fail");
            return null;
        }
        return problem;
    }

    @CachePut(key = "#problem.problemId", unless = "#result == null")
    public Problem updateByPrimaryKey(Problem problem) {
        if (problemMapper.updateByPrimaryKey(problem) != 1) {
            return null;
        }
        solutionMapper.updateByProblemId(problem.getProblemId());
        if (!problemFileUtil.createProblemFile(problem)) {
            return null;
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
     * @return: java.utils.List<com.yoj.model.entity.Problem>
     * @Author: lmz
     * @Date: 2019/10/23
     */
    public List<Problem> getProblemList(Problem problem) {
        return problemMapper.getProblemList(problem);
    }

    @Transactional
    @CacheEvict(key = "#pid")
    public boolean deleteProblemById(Integer pid) {
        if (problemMapper.deleteProblemById(pid) != 1) {
            return false;
        }
        solutionMapper.deleteByProblemId(pid);
        return true;
    }

    public boolean queryById(Integer problemId) {
        return problemMapper.queryById(problemId) != null;
    }

}
