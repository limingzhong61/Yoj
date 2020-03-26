package com.yoj.controller.admin;

import com.yoj.model.entity.Problem;
import com.yoj.model.vo.Msg;
import com.yoj.service.ProblemService;
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

    @GetMapping("/{pid}")
    public Msg getProblemAll(@PathVariable("pid") Integer pid) {
        Problem problem = problemService.getAllById(pid);
        if (problem == null) {
            return Msg.fail();
        }
        return Msg.success().add("problem", problem);
    }

//    @PutMapping("/updateProblemId")
//    public Msg updateProblemId() {
//
//    }
}
