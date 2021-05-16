package com.yoj.controller.admin;

import com.yoj.model.vo.Msg;
import com.yoj.service.SolutionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@PreAuthorize("hasRole('ADMIN')")
@RequestMapping("/admin/solution")
public class SolutionAdminController {
    @Autowired
    SolutionService solutionService;

    @DeleteMapping("/{pid}")
    public Msg delSolutionById(@PathVariable("pid") Integer SolutionId) {
        if (!solutionService.delSolutionById(SolutionId)) {
            return Msg.fail();
        }
        return Msg.success();
    }
}
