package com.yoj.controller.admin;

import com.yoj.model.entity.Problem;
import com.yoj.model.vo.Msg;
import com.yoj.service.ProblemService;
import com.yoj.utils.file.ProblemFileUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@PreAuthorize("hasRole('ADMIN')")
@RequestMapping("/admin")
public class AdminController {
    @Autowired
    public ProblemService problemService;
    @Autowired
    private ProblemFileUtil problemFileUtil;

    @GetMapping("/updateAllProblemFile")
    public Msg createNeededProblemFile() {
        List<Problem> problems = problemService.getAll();
        for(Problem problem : problems){
            problemFileUtil.createProblemFile(problem);
        }
        return Msg.success();
    }
}
