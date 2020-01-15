package com.yoj.web.controller.admin;

import com.yoj.web.pojo.Problem;
import com.yoj.web.pojo.util.Msg;
import com.yoj.web.service.ProblemService;
import com.yoj.web.util.ProblemUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@PreAuthorize("hasRole('ADMIN')")
@RequestMapping("/admin/problem")
public class AdminProblemController {
    @Autowired
    private ProblemService problemService;
    @Autowired
    private ProblemUtil problemUtil;

    @GetMapping("/{pid}")
    public Msg getProblemAll(@PathVariable("pid") Integer pid) {
        Problem problem = problemService.getAllById(pid);
        if (problem == null) {
            return Msg.fail();
        }
        problemUtil.changeDataToList(problem);
        return Msg.success().add("problem", problem);
    }

//    @PutMapping("/updateProblemId")
//    public Msg updateProblemId() {
//
//    }
}
