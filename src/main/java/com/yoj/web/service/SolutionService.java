package com.yoj.web.service;

import com.yoj.custom.judge.enums.JudgeResult;
import com.yoj.web.dao.SolutionMapper;
import com.yoj.web.pojo.Solution;
import com.yoj.web.util.auth.CurrentUserUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;

@CacheConfig(cacheNames = "solution")
@Service
@Slf4j
public class SolutionService {
    @Autowired
    private SolutionMapper solutionMapper;

    @Autowired
    private UserService userService;

    @Autowired
    private CurrentUserUtil userUtils;

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
        //judging
        solution.setResult(JudgeResult.JUDGING.ordinal());
        if (solutionMapper.insertSelective(solution) > 0) {
            return solution;
        }
        return null;
    }

    @Cacheable
    public Solution getById(Integer sid) {
        return solutionMapper.getById(sid);
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
    public List<Solution> getListBySelective(Solution solution) {
        return solutionMapper.getListBySelective(solution);
    }

    public int countAcceptedByProblemId(Integer pid) {
        return solutionMapper.countAcceptedByProblemId(pid);
    }

    public int countSubmissionByProblemId(Integer pid) {
        return solutionMapper.countSubmissionByProblemId(pid);
    }

    /**
     * @Description: 根据输入的solution动态查询问题集合
     * @Param: [solution]
     * @return: java.util.List<com.yoj.web.pojo.Solution>
     * @Author: lmz
     * @Date: 2019/10/22
     */
    public Long countBySelective(Solution solution) {
        return solutionMapper.countBySelective(solution);
    }


    public Long countAcceptedByUser() {
        Solution solution = new Solution();
        solution.setUserName(userUtils.getUserDetail().getUsername());
        solution.setResult(JudgeResult.ACCEPTED.ordinal());
        return solutionMapper.countBySelective(solution);
    }


    public Long countSubmissionByUser() {
        Solution solution = new Solution();
        solution.setUserName(userUtils.getUserDetail().getUsername());
        return solutionMapper.countBySelective(solution);
    }


    public Integer countAcceptedByUserId(Integer userId) {
        return solutionMapper.countAcceptedByUserId(userId);
    }

    public Integer countSubmissionsByUserId(Integer userId) {
        return solutionMapper.countSubmissionsByUserId(userId);
    }

    public Integer countAttemptedByUserId(Integer userId) {
        return solutionMapper.countAttemptedByUserId(userId);
    }

    public Integer countSolvedByUserId(Integer userId) {
        return solutionMapper.countSolvedByUserId(userId);
    }

    @CachePut(key = "#result.solutionId",unless ="#result == null")
    public Solution updateById(Solution updateSolution) {
        if(solutionMapper.updateById(updateSolution) > 0){
            return solutionMapper.getById(updateSolution.getSolutionId());
        }
        return null;
    }
}
