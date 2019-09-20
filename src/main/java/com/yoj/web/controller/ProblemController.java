package com.yoj.web.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.yoj.nuts.auth.UserUtils;
import com.yoj.web.bean.Msg;
import com.yoj.web.bean.Problem;
import com.yoj.web.bean.User;
import com.yoj.web.service.ProblemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/p")
public class ProblemController {

    @Autowired
    private ProblemService problemService;

    @RequestMapping("/{pid}")
    public String getProblem(@PathVariable("pid") Integer pid, Map<String, Object> map, HttpServletRequest request) {
        request.getSession().setAttribute("pid", pid);
        Problem problem = problemService.queryById(pid);
        User user = UserUtils.getUser();
        if (user != null && problem.getUserId() == user.getUserId()) {
            map.put("alter", true);
        }
        map.put("problem", problem);
        return "problem/problemView";
    }

    @RequestMapping("/{pid}/submit")
    public String toSubmitView(@PathVariable("pid") int pid, Map<String, Object> map) {
        map.put("pid", pid);
        return "problem/submit";
    }


    @RequestMapping("/main/{pageNumber}")
    @ResponseBody
    public Msg result(@PathVariable("pageNumber") Integer pageNumber, HttpServletRequest request) {
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

    @RequestMapping("/add")
    @ResponseBody
    public Msg addProblem(Problem problem) {
        User user = UserUtils.getUser();
        if (user == null) {
            return Msg.fail("");
        }
        problem.setUserId(user.getUserId());
        if (problemService.insert(problem)) {
            return Msg.success().add("pid", problem.getProblemId());
        }
        return Msg.fail();
    }

    @GetMapping("/alter/{pid}")
    public String toAddProblem(@PathVariable("pid") Integer pid, Model model) {
        model.addAttribute("problem", problemService.queryById(pid).parseJudgeData());
        return "problem/addProblem";
    }

    /**
     * @Description:
     * @Param: [pid, model]
     * @return: java.lang.String
     * @Author: lmz
     */
    @RequestMapping("/alter")
    @ResponseBody
    public Msg alterProblem(Problem problem) {
        User user = UserUtils.getUser();
        if (user == null) {
            return Msg.fail("");
        }
        problem.setUserId(user.getUserId());
        if (problemService.updateByPrimaryKey(problem)) {
            return Msg.success().add("pid", problem.getProblemId());
        }
        return Msg.fail();
    }
}
