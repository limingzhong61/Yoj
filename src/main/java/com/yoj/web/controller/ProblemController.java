package com.yoj.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

@Controller
@RequestMapping("/problem")
public class ProblemController {

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

//    @PostMapping("/submit")
//    public String submit(Problem problem) {
//
//        return "/problem/submit";
//    }
}
