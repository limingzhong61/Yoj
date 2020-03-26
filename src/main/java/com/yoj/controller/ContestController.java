package com.yoj.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.yoj.model.entity.Contest;
import com.yoj.model.entity.Problem;
import com.yoj.model.entity.Solution;
import com.yoj.model.entity.User;
import com.yoj.model.vo.Msg;
import com.yoj.model.pojo.util.UserDetailsImpl;
import com.yoj.service.ContestProblemService;
import com.yoj.service.ContestService;
import com.yoj.service.SolutionService;
import com.yoj.utils.ContestUtil;
import com.yoj.utils.auth.CurrentUserUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/contest")
public class ContestController {
    @Autowired
    private ContestService contestService;
    @Autowired
    private ContestProblemService contestProblemService;
    @Autowired
    private CurrentUserUtil currentUserUtil;
    @Autowired
    private ContestUtil contestUtil;
    @Autowired
    private SolutionService solutionService;

    @GetMapping("/set/{pageNumber}")
    public Msg getSet(@PathVariable("pageNumber") Integer pageNumber, Contest contest) {
        PageHelper.startPage(pageNumber, 10);
        List<Contest> contests = contestService.getList(contest);
        PageInfo<Contest> page = new PageInfo(contests, 5);
        return Msg.success().add("pageInfo", page);
    }

    /**
     * request too many information... can optimize
     * @param cid
     * @return
     */
    @GetMapping("/view/{cid}")
    public Msg getContestView(@PathVariable("cid") Integer cid) {
        Contest contest = contestService.getById(cid);
        if (contest == null) {
            return Msg.fail();
        }
        Msg msg = Msg.success();
        contest.setStatus(contestUtil.computeContestStatus(contest));
        // already started
        if (contest.getStatus() >= 0) {
            //get problems of contest
            List<Problem> contestProblemList = contestProblemService.getContestProblemListByContestId(contest.getContestId());
            msg.add("contestProblemList", contestProblemList);
            // get solutions of user in this contest
            UserDetailsImpl userDetail = currentUserUtil.getUserDetail();
            if (userDetail != null) {
                if (contestUtil.isStarted(contest)) {
                    List<Solution> solutions = solutionService.getUserContestRecord(contest.getContestId(), userDetail.getUserId());
                    msg.add("solutionList", solutions);
                }
            }
        }
        // is already end
        if (contest.getStatus() == 1) {
            // get contest rank
            if (contestUtil.isStarted(contest)) {
                List<User> users = solutionService.getContestRankByContestId(contest.getContestId());
                msg.add("userList", users);
            }
        }
        return msg.add("contest", contest);
    }
}
