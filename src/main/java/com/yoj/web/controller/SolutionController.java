package com.yoj.web.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.yoj.judge.Judge;
import com.yoj.web.bean.Solution;
import com.yoj.web.bean.User;
import com.yoj.web.service.SolutionService;

@Controller
@RequestMapping("/solution")
public class SolutionController {

    @Autowired
    public SolutionService solutionService;

    
    @PostMapping("/submit")
    public String submit(Solution solution, HttpServletRequest request) {
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");
        if (user == null) {
            request.setAttribute("msg", "提交代码前请先登录");
            return "/problem/submit";
        }
        solution.setUserId(user.getUserId());
        solution.setProblemId((Integer) session.getAttribute("pid"));
        Judge.judge(solution);

        solution.setResult("ac");


        int result = solutionService.insertSolution(solution);
        
        return "/problem/submit";
    }
}
