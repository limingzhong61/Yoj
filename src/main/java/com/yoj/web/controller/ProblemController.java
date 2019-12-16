package com.yoj.web.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.yoj.web.pojo.Problem;
import com.yoj.web.pojo.User;
import com.yoj.web.pojo.satic.RoleName;
import com.yoj.web.pojo.util.Msg;
import com.yoj.web.service.ProblemService;
import com.yoj.web.util.auth.CurrentUserUtil;
import org.springframework.beans.factory.annotation.Autowired;
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
    public Msg getProblem(@PathVariable("pid") Integer pid) {
        Problem problem = problemService.queryById(pid);
        Msg msg = Msg.success().add("problem", problem);
        User user = userUtil.getUser();
        if (user != null && problem.getUserId() == user.getUserId()) {
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
        User user = userUtil.getUser();
        Msg msg = Msg.success();
        Collection<? extends GrantedAuthority> authorities = userUtil.getAuthorities();
        // 只有管理员才能添加
        if (authorities.contains(RoleName.ADMIN)) {
            msg.add("add", true);
        }
        //only login can set
        if (user != null) {
            problem.setUserId(user.getUserId());
        }
        PageHelper.startPage(pageNumber, 10);
        List<Problem> problems = problemService.getProblemList(problem);
        PageInfo<Problem> page = new PageInfo<Problem>(problems, 5);
        return msg.add("pageInfo", page);
    }

    @PostMapping("/add")
    public Msg addProblem(@RequestBody Problem problem) {
        User user = userUtil.getUser();
        if (user == null) {
            return Msg.fail("");
        }

        Collection<? extends GrantedAuthority> authorities = userUtil.getAuthorities();
        // 只有管理员才能添加
        if (!authorities.contains(RoleName.ADMIN)) {
            return Msg.fail("对不起，你没有添加题目的权限");
        }
        problem.setUserId(user.getUserId());
        if (problemService.insert(problem) != null) {
            return Msg.success().add("pid", problem.getProblemId());
        }
        return Msg.fail();
    }

    /**
     * @Description:
     * @Param: [pid, model]
     * @return: java.lang.String
     * @Author: lmz
     */
    @PostMapping("/alter")
    public Msg alterProblem(@RequestBody Problem problem) {
        User user = userUtil.getUser();
        Collection<? extends GrantedAuthority> authorities = userUtil.getAuthorities();
        //先判断是否是出题人
        if (user == null || !authorities.contains(RoleName.ADMIN)) {
            return Msg.fail("对不起，你不具有修改此题的权限");
        }
        problem.setUserId(user.getUserId());
        if (problemService.updateByPrimaryKey(problem) != null) {
            return Msg.success().add("pid", problem.getProblemId());
        }
        return Msg.fail();
    }

}
