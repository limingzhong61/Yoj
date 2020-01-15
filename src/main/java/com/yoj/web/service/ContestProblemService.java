package com.yoj.web.service;

import com.yoj.web.dao.ContestProblemMapper;
import com.yoj.web.pojo.Contest;
import com.yoj.web.pojo.Problem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ContestProblemService {
    @Autowired
    private ContestProblemMapper contestProblemMapper;

    public List<Problem> getContestProblemListByContestId(Integer contestId) {
        return contestProblemMapper.getContestProblemListByContestId(contestId);
    }

    public boolean queryHaveContestProblem(Contest contest) {
        return contestProblemMapper.queryHaveContestProblem(contest) > 0;
    }

}
