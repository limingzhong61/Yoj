package com.yoj.controller.admin;

import com.yoj.model.entity.Contest;
import com.yoj.model.entity.ContestProblem;
import com.yoj.model.vo.Msg;
import com.yoj.model.pojo.util.UserDetailsImpl;
import com.yoj.service.ContestService;
import com.yoj.service.ProblemService;
import com.yoj.utils.auth.CurrentUserUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@PreAuthorize("hasRole('ADMIN')")
@RequestMapping("/admin/contest")
public class AdminContestController {
    @Autowired
    private ContestService contestService;
    @Autowired
    private ProblemService problemService;
    @Autowired
    private CurrentUserUtil currentUserUtil;

    @PostMapping("/add")
    public Msg getProblemAll(@RequestBody Contest contest) {
        UserDetailsImpl userDetail = currentUserUtil.getUserDetail();
        for(ContestProblem contestProblem : contest.getContestProblemList()){
            if(!problemService.queryById(contestProblem.getProblemId())){
                return Msg.fail("问题"+contestProblem.getProblemId() +"不存在");
            }
        }
        contest.setUserId(userDetail.getUserId());
        if (!contestService.insert(contest)) {
            return Msg.fail();
        }
        return Msg.success();
    }

    @PutMapping("/alter")
    public Msg updateContestById(@RequestBody Contest contest) {
        if (!contestService.updateById(contest)) {
            return Msg.fail();
        }
        return Msg.success();
    }
}
