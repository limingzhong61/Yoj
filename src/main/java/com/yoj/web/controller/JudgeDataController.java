package com.yoj.web.controller;

import com.yoj.web.pojo.Solution;
import com.yoj.web.service.SolutionService;
import com.yoj.web.util.CaseFileUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/data")
public class JudgeDataController {
    @Autowired
    private CaseFileUtil caseFileUtil;
    @Autowired
    private SolutionService solutionService;

    /**
     *
     * @param solutionId only download by solution,prevent judge data 被大量爬取
     * @param caseId
     * @param inOrOut
     * @return
     */
    @GetMapping("/get")
    @PreAuthorize("isAuthenticated()")  //need login
    public ResponseEntity<byte[]> getCurrentUserInfo(Integer solutionId, Integer caseId, Integer inOrOut) {
        Solution solution = solutionService.getById(solutionId);
        if(solution == null){
            return null;
        }
        return caseFileUtil.getCaseFile(solution.getProblemId(), caseId, inOrOut);
    }


}
