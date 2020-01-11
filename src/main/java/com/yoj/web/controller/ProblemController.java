package com.yoj.web.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.yoj.web.pojo.Problem;
import com.yoj.web.pojo.satic.RoleName;
import com.yoj.web.pojo.util.Msg;
import com.yoj.web.pojo.util.UserDetailsImpl;
import com.yoj.web.service.ProblemService;
import com.yoj.web.util.auth.CurrentUserUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.List;

@RestController
@RequestMapping("/problem")
public class ProblemController {

    @Autowired
    private ProblemService problemService;
    @Autowired
    private CurrentUserUtil userUtil;

    @GetMapping("/{pid}")
    public Msg getViewProblem(@PathVariable("pid") Integer pid) {
        Problem problem = problemService.getViewInfoById(pid);
        Msg msg = Msg.success().add("problem", problem);
        UserDetailsImpl userDetail = userUtil.getUserDetail();
        if (userDetail != null && problem.getUserId() == userDetail.getUserId()) {
            msg.add("alter", true);
        } else {
            // judge is or not admin
            Collection<? extends GrantedAuthority> authorities = userUtil.getAuthorities();
            if (authorities.contains(RoleName.ADMIN)) {
                msg.add("alter", true);
            }
        }
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
    // 只有管理员才能添加
    @PreAuthorize("hasRole('ADMIN')")
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
    // 只有管理员才能添加
    @PreAuthorize("hasRole('ADMIN')")
    public Msg alterProblem(@RequestBody Problem problem) {
        UserDetailsImpl userDetail = userUtil.getUserDetail();
        Collection<? extends GrantedAuthority> authorities = userUtil.getAuthorities();
        problem.setUserId(userDetail.getUserId());
        if (problemService.updateByPrimaryKey(problem) != null) {
            return Msg.success().add("pid", problem.getProblemId());
        }
        return Msg.fail();
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{pid}")
    public Msg deleteProblem(@PathVariable("pid")Integer pid) {
       if(!problemService.deleteProblemById(pid)){
           return Msg.fail();
       }
        return Msg.success();
    }


}
