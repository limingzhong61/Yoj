package com.yoj.web.controller.admin;

import com.yoj.web.pojo.Contest;
import com.yoj.web.pojo.util.Msg;
import com.yoj.web.pojo.util.UserDetailsImpl;
import com.yoj.web.service.ContestService;
import com.yoj.web.util.auth.CurrentUserUtil;
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
    private CurrentUserUtil currentUserUtil;

    @PostMapping("/add")
    public Msg getProblemAll(@RequestBody Contest contest) {
        UserDetailsImpl userDetail = currentUserUtil.getUserDetail();
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
