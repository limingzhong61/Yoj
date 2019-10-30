package com.yoj.web.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.yoj.nuts.auth.UserUtils;
import com.yoj.nuts.judge.Judge;
import com.yoj.web.bean.Problem;
import com.yoj.web.bean.Solution;
import com.yoj.web.bean.User;
import com.yoj.web.bean.util.Msg;
import com.yoj.web.service.ProblemService;
import com.yoj.web.service.SolutionService;
import com.yoj.web.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/solution")
public class SolutionController {
    @Autowired
    public SolutionService solutionService;
    @Autowired
    public ProblemService problemService;
    @Autowired
    private Judge Judge;
    @Autowired
    public UserService UserService;
    @Autowired
    public UserUtils userUtils;

    @PostMapping("/submit")
    public Msg submit(@RequestBody Solution solution) {
        User user = userUtils.getCurrentUser();
        if (user == null) {
            return Msg.fail("提交代码前请先登录");
        }
        solution.setUserId(user.getUserId());
        solution.setUserName(user.getUserName());
        Problem problem = problemService.queryById(solution.getProblemId());
        Judge.judge(solution, problem);
        //insert fail
        if (solutionService.insertSolution(solution) == null) {
            return Msg.fail("insert solution fail");
        }
        return Msg.success();
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
    public Msg detail(@PathVariable("solutionId") Integer solutionId) {
        Solution solution = solutionService.getById(solutionId);
        User user = userUtils.getCurrentUser();
        if (solution.getShare() == 1 || user.getUserId() == solution.getUserId()) {
            return Msg.success().add("solution", solution);
        }
        return Msg.fail("用户未分享该代码");
    }
}
