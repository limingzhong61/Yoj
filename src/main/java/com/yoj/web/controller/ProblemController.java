package com.yoj.web.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.yoj.nuts.auth.UserUtils;
import com.yoj.web.bean.Problem;
import com.yoj.web.bean.User;
import com.yoj.web.bean.util.Msg;
import com.yoj.web.service.ProblemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;

@RestController
@RequestMapping("/problem")
public class ProblemController {

    @Autowired
    private ProblemService problemService;
    @Autowired
    private UserUtils userUtils;

    @GetMapping("/{pid}")
    public Msg getProblem(@PathVariable("pid") Integer pid) {
        Problem problem = problemService.queryById(pid);
        Msg msg = Msg.success().add("problem", problem);
        User user = userUtils.getCurrentUser();
        if (user != null && problem.getUserId() == user.getUserId()) {
            msg.add("alter", true);
        }
        return msg;
    }

    @GetMapping("/getProblemSet/{pageNumber}")
    public Msg getProblemSet(@PathVariable("pageNumber") Integer pageNumber, HttpServletRequest request) {
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");

        PageHelper.startPage(pageNumber, 10);
        List<Problem> problems = problemService.getAll();
        PageInfo<Problem> page = new PageInfo<Problem>(problems, 5);
        //多表查询。。。。。
        for (Problem problem : page.getList()) {
            problem.setAcRate((int) Math.round(problem.getAccepted() * 100.0 / problem.getSubmissions()));
            if (user != null) {
                problem.setSolved(problemService.isSolved(problem.getProblemId(), user.getUserId()));
            }
        }
        return Msg.success().add("pageInfo", page);
    }

    @PostMapping("/add")
    public Msg addProblem(@RequestBody Problem problem) {
        User user = userUtils.getCurrentUser();
        if (user == null) {
            return Msg.fail("");
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
        User user = userUtils.getCurrentUser();
        //先判断是否是出题人
        if (user == null || user.getUserId() != problemService.queryById(problem.getProblemId()).getUserId()) {
            return Msg.fail("你不具有修改此题的权限");
        }
        problem.setUserId(user.getUserId());
        if (problemService.updateByPrimaryKey(problem) != null) {
            return Msg.success().add("pid", problem.getProblemId());
        }
        return Msg.fail();
    }
}
