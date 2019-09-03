package com.yoj.web.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.yoj.web.bean.Msg;
import com.yoj.web.bean.Problem;
import com.yoj.web.service.ProblemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/p")
public class ProblemController {
	
	@Autowired
	private ProblemService problemService;

    //@ResponseBody
    @RequestMapping("/{pid}")
    public String getProblem(@PathVariable("pid") Integer pid, HttpServletRequest request) {
        request.getSession().setAttribute("pid",pid);
        return "/problem/problemView";
    }

    @RequestMapping("/{pid}/submit")
    public String toSubmitView(@PathVariable("pid") int pid, Map<String, Object> map) {
        return "/problem/submit";
    }
    

    @RequestMapping("/main/{pageNumber}")
    @ResponseBody
    public Msg result(@PathVariable("pageNumber") Integer pageNumber) {
        PageHelper.startPage(pageNumber, 10);
        //需要获得用户名
        List<Problem> problems = problemService.getAll();
        PageInfo<Problem> page = new PageInfo<Problem>(problems, 5);
        for(Problem problem : page.getList()){
            problem.setAcRate((int) Math.round(problem.getAccepted()*100.0/problem.getSubmissions()));
        }
        return Msg.success().add("pageInfo", page);
    }
}
