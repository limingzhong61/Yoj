package com.yoj.web.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.yoj.custom.judge.Judge;
import com.yoj.custom.judge.bean.JudgeSource;
import com.yoj.custom.judge.enums.JudgeResult;
import com.yoj.custom.judge.threads.JudgeThreadPoolManager;
import com.yoj.web.pojo.Contest;
import com.yoj.web.pojo.Problem;
import com.yoj.web.pojo.Solution;
import com.yoj.web.pojo.util.Msg;
import com.yoj.web.pojo.util.UserDetailsImpl;
import com.yoj.web.service.*;
import com.yoj.web.util.ContestUtil;
import com.yoj.web.util.auth.CurrentUserUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/solution")
public class SolutionController {
    @Autowired
    private SolutionService solutionService;
    @Autowired
    private CurrentUserUtil userUtils;

    @Autowired
    private ContestService contestService;
    @Autowired
    private ContestProblemService contestProblemService;
    @Autowired
    private ContestUtil contestUtil;
    @Autowired
    private Judge judge;

    /**
     * test thread pool with out authority，don't delete it.
     *
     * @param solution
     * @return
     */
//    @PostMapping("/submit/test")
//    public Msg testThreadPool(@RequestBody  Solution solution) {
//        solution.setUserId(1);
//        JudgeSource judgeSource = new JudgeSource();
//        Problem problem = problemService.getViewInfoById(solution.getProblemId());
//        BeanUtils.copyProperties(solution,judgeSource);
//        judgeSource.setMemoryLimit(problem.getMemoryLimit());
//        judgeSource.setTimeLimit(problem.getTimeLimit());
//        Solution insertSolution = solutionService.insertSolution(solution);
//        if (insertSolution == null) {
//            return Msg.fail("insert solution fail");
//        }
//        judgeSource.setSolutionId(insertSolution.getSolutionId());
//        threadPoolManager.addTask(judgeSource);
//        return Msg.success();
//    }
    @PreAuthorize("isAuthenticated()")
    @PostMapping("/submit")
    public Msg submit(@RequestBody Solution solution) {
        UserDetailsImpl userDetail = userUtils.getUserDetail();
        if (userDetail == null) {
            return Msg.fail("提交代码前请先登录");
        }
        Integer contestId = solution.getContestId();
        //have contest ?
        if (contestId != null) {
            Contest contest = contestService.getFromContestProblem(contestId, solution.getProblemId());
            // judge this problem whether in this contest
            if (contest == null) {
                return Msg.fail("本次比赛没有此题");
            }
            // isn't contesting
            if (!contestUtil.isStarted(contest)) {
                return Msg.fail("比赛尚未开始");
            } else if (contestUtil.isEnd(contest)) {
                return Msg.fail("比赛已经结束");
            }
        }
        solution.setUserId(userDetail.getUserId());
        Solution insertSolution = solutionService.insertSolution(solution);
        if (insertSolution == null) {
            return Msg.fail("insert solution fail");
        }
        return solutionService.submit(solution);
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/reSubmit/{sid}")
    public Msg reSubmit(@PathVariable("sid") Integer sid) {
        Solution solution = solutionService.getById(sid);
        if(solution.getResult() != JudgeResult.WAIT_REJUDGE.ordinal()){
            return Msg.fail("Solution doesn't need rejudge");
        }
        return solutionService.submit(solution);
    }

    @GetMapping("/set/{pageNumber}")
    public Msg result(@PathVariable("pageNumber") Integer pageNumber, Solution solution) {
        PageHelper.startPage(pageNumber, 10);
        //需要获得用户名
        List<Solution> solutions = solutionService.getListBySelective(solution);
        PageInfo<Solution> page = new PageInfo<Solution>(solutions, 5);
        return Msg.success().add("pageInfo", page);
    }

    @GetMapping("/detail/{solutionId}")
    @PreAuthorize("isAuthenticated()")
    public Msg detail(@PathVariable("solutionId") Integer solutionId) {
        Solution solution = solutionService.getById(solutionId);
        UserDetailsImpl userDetail = userUtils.getUserDetail();
        if (solution.getShare() == 1 || userDetail.getUserId().equals(solution.getUserId())) {
            return Msg.success().add("solution", solution);
        }
        return Msg.fail("用户未分享该代码");
    }
}
