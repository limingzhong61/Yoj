package com.yoj.web.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.yoj.web.bean.Msg;
import com.yoj.web.bean.Problem;
import com.yoj.web.bean.User;
import com.yoj.web.bean.UserDetailsImpl;
import com.yoj.web.service.ProblemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/p")
public class ProblemController {
	
	@Autowired
	private ProblemService problemService;

    //@ResponseBody
    @RequestMapping("/{pid}")
    public String getProblem(@PathVariable("pid") Integer pid,Map<String,Object> map, HttpServletRequest request) {
        request.getSession().setAttribute("pid",pid);
        Problem problem = problemService.queryById(pid);
        map.put("problem",problem);
        return "problem/problemView";
    }

    @RequestMapping("/{pid}/submit")
    public String toSubmitView(@PathVariable("pid") int pid, Map<String, Object> map) {
        map.put("pid",pid);
        return "problem/submit";
    }
    

    @RequestMapping("/main/{pageNumber}")
    @ResponseBody
    public Msg result(@PathVariable("pageNumber") Integer pageNumber,HttpServletRequest request) {
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");

        PageHelper.startPage(pageNumber, 10);
        List<Problem> problems = problemService.getAll();
        PageInfo<Problem> page = new PageInfo<Problem>(problems, 5);
        //多表查询。。。。。
        for(Problem problem : page.getList()){
            problem.setAcRate((int) Math.round(problem.getAccepted()*100.0/problem.getSubmissions()));
            if (user != null) {
                problem.setSolved(problemService.isSolved(problem.getProblemId(),user.getUserId()));
            }
        }
        return Msg.success().add("pageInfo", page);
    }

    @PostMapping("add")
    public String addProblem(Problem problem){
        UserDetailsImpl userDetails = (UserDetailsImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User user = (User) userDetails.getUser();
        if(user == null){
            return "problem_set";
        }
        problem.setUserId(user.getUserId());
        if(problemService.insert(problem)) {
            ;
        }
        return "problem_set";
    }
}
