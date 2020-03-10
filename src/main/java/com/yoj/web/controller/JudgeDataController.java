package com.yoj.web.controller;

import com.yoj.web.pojo.Solution;
import com.yoj.web.service.DownloadCntService;
import com.yoj.web.service.SolutionService;
import com.yoj.web.util.CaseFileUtil;
import com.yoj.web.util.auth.CurrentUserUtil;
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
    @Autowired
    private CurrentUserUtil currentUserUtil;
    @Autowired
    private DownloadCntService downloadCntService;

    /**
     * @param solutionId only download by solution,prevent judge data 被大量爬取
     * @param caseId
     * @param inOrOut
     * @return
     */
    @GetMapping("/get")
    @PreAuthorize("isAuthenticated()")  //need login
    public ResponseEntity<byte[]> getCurrentUserInfo(Integer solutionId, Integer caseId, Integer inOrOut) {
        Integer userId = currentUserUtil.getUserDetail().getUserId();
        Integer downloadCnt = downloadCntService.getDownloadCnt(userId);
        if (downloadCnt > 10) {
//            return Msg.fail("download too mach");
            return null;
        }
        Solution solution = solutionService.getById(solutionId);
        if (solution == null) {
            return null;
        }
        ResponseEntity<byte[]> caseFile = caseFileUtil.getCaseFile(solution.getProblemId(), caseId, inOrOut);
        if (caseFile == null) {
            return null;
        }
        if (downloadCntService.update(userId,downloadCnt) == null) {
            return null;
        }
        return caseFile;
    }
}
