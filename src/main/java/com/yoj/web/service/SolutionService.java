package com.yoj.web.service;

import com.yoj.custom.judge.bean.JudgeSource;
import com.yoj.custom.judge.enums.JudgeResult;
import com.yoj.custom.judge.threads.JudgeThreadPoolManager;
import com.yoj.web.dao.SolutionMapper;
import com.yoj.web.pojo.Problem;
import com.yoj.web.pojo.Solution;
import com.yoj.web.pojo.User;
import com.yoj.web.pojo.util.Msg;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@CacheConfig(cacheNames = "solution")
@Service
@Slf4j
public class SolutionService {
    @Autowired
    private SolutionMapper solutionMapper;

    @Autowired
    private JudgeThreadPoolManager threadPoolManager;

    @Autowired
    private  ProblemService problemService;

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

    @Cacheable(unless = "#result == null")
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
        solution.setResult(JudgeResult.ACCEPTED.ordinal());
        return solutionMapper.countBySelective(solution);
    }


    public Long countSubmissionByUser() {
        Solution solution = new Solution();
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

    @CachePut(key = "#result.solutionId", unless = "#result == null")
    public Solution updateById(Solution updateSolution) {
        if (solutionMapper.updateById(updateSolution) > 0) {
            return solutionMapper.getById(updateSolution.getSolutionId());
        }
        return null;
    }


    public List<Solution> getUserContestRecord(Integer contestId, Integer userId) {
        return solutionMapper.getUserContestRecord(contestId, userId);
    }

    public List<User> getContestRankByContestId(Integer contestId) {
        // get contest-solution
        List<Solution> solutions = solutionMapper.getByContestId(contestId);
        HashMap<Integer, User> map = new HashMap<>();

        for (Solution solution : solutions) {
            if (!map.containsKey(solution.getUserId())) {
                User user = new User();
                user.setUserId(solution.getUserId());
                user.setNickName(solution.getNickName());
                user.setScore(solution.getScore());
                user.setTotalRunTime(solution.getRuntime());
                map.put(solution.getUserId(), user);
            } else {
                User user = map.get(solution.getUserId());
                user.setScore(user.getScore() + solution.getScore());
                user.setTotalRunTime(user.getTotalRunTime() + solution.getRuntime());
                map.put(solution.getUserId(), user);
            }
        }
        List<User> users = new ArrayList(map.values());
        users.sort((User a, User b) -> {
            if (a.getScore() != b.getScore()) {
                // bigger at front
                return b.getScore() - a.getScore();
            } else {
                // shorter at front
                return a.getTotalRunTime() - b.getTotalRunTime();
            }
        });
        return users;
    }

    public Msg submit(Solution solution) {
        JudgeSource judgeSource = new JudgeSource();
        BeanUtils.copyProperties(solution, judgeSource);
        judgeSource.setSolutionId(solution.getSolutionId());
        Problem problem = problemService.getViewInfoById(solution.getProblemId());
        judgeSource.setMemoryLimit(problem.getMemoryLimit());
        judgeSource.setTimeLimit(problem.getTimeLimit());
        threadPoolManager.addTask(judgeSource);
        return Msg.success();
    }
}
