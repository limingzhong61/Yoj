package com.yoj.controller;

import com.yoj.annotation.JudgePermitAnnotation;
import com.yoj.model.entity.Solution;
import com.yoj.service.SolutionService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
@ConditionalOnProperty(prefix="judge",name = "local", havingValue = "true")
public class JudgeController {
    @Autowired
    private SolutionService solutionService;

    @PostMapping("/result")
    @JudgePermitAnnotation
    public void judge(@RequestBody Solution solution) {
        log.info("result get solution{}",solution);
        solutionService.updateAfterJudge(solution);
    }
}
