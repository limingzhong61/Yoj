package com.yoj.web.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.yoj.web.pojo.Contest;
import com.yoj.web.pojo.util.Msg;
import com.yoj.web.service.ContestService;
import com.yoj.web.util.auth.CurrentUserUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/contest")
public class ContestController {
    @Autowired
    private ContestService contestService;
    @Autowired
    private CurrentUserUtil currentUserUtil;

    @GetMapping("/set/{pageNumber}")
    public Msg getSet(@PathVariable("pageNumber")Integer pageNumber,Contest contest){
        PageHelper.startPage(pageNumber, 10);
        List<Contest> contests = contestService.getList(contest);
        PageInfo<Contest> page = new PageInfo(contests, 5);
        return Msg.success().add("pageInfo", page);
    }

    @GetMapping("/view/{cid}")
    public Msg getContestView(@PathVariable("cid")Integer cid){
        Contest contest = contestService.getById(cid);
        if(contest == null){
            return Msg.fail();
        }
        return Msg.success().add("contest", contest);
    }

}
