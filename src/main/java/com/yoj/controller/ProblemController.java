package com.yoj.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.yoj.model.entity.Problem;
import com.yoj.model.vo.Msg;
import com.yoj.model.pojo.util.UserDetailsImpl;
import com.yoj.service.ProblemService;
import com.yoj.utils.auth.UserUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/problem")
public class ProblemController {

    @Autowired
    private ProblemService problemService;
    @Autowired
    private UserUtil userUtil;

    @GetMapping("/{pid}")
    public Msg getViewProblem(@PathVariable("pid") Integer pid) {
        Problem problem = problemService.getViewInfoById(pid);
        Msg msg = Msg.success().add("problem", problem);
        return msg;
    }

    @GetMapping("/getProblemSet/{pageNumber}")
    public Msg getProblemSet(@PathVariable("pageNumber") Integer pageNumber, Problem problem) {
        UserDetailsImpl userDetail = userUtil.getUserDetail();
        Msg msg = Msg.success();
        //only login can set
        if (userDetail != null) {
            problem.setUserId(userDetail.getUserId());
        }
        PageHelper.startPage(pageNumber, 10);
        List<Problem> problems = problemService.getProblemList(problem);
        PageInfo<Problem> page = new PageInfo<Problem>(problems, 5);
        return msg.add("pageInfo", page);
    }

    @PostMapping("/add")
    @PreAuthorize("hasRole('ADMIN')") // 只有管理员才能添加
    public Msg addProblem(@RequestBody Problem problem) {
        UserDetailsImpl userDetail = userUtil.getUserDetail();
        if (userDetail == null) {
            return Msg.fail("");
        }
        problem.setUserId(userDetail.getUserId());
        if (problemService.insert(problem) != null) {
            return Msg.success().add("pid", problem.getProblemId());
        }
        return Msg.fail();
    }

    /**
     * @Description: alter Problem
     * @Param: [pid, model]
     * @return: java.lang.String
     * @Author: lmz
     */
    @PutMapping("/alter")
    @PreAuthorize("hasRole('ADMIN')")// 只有管理员才能添加
    public Msg alterProblem(@RequestBody Problem problem) {
        UserDetailsImpl userDetail = userUtil.getUserDetail();
        problem.setUserId(userDetail.getUserId());
        if (problemService.updateByPrimaryKey(problem) != null) {
            return Msg.success().add("pid", problem.getProblemId());
        }
        return Msg.fail();
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{pid}")
    public Msg deleteProblem(@PathVariable("pid") Integer pid) {
        if (!problemService.deleteProblemById(pid)) {
            return Msg.fail();
        }
        return Msg.success();
    }


}
