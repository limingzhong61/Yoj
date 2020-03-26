package com.yoj.service;

import com.yoj.mapper.ContestProblemMapper;
import com.yoj.model.entity.Contest;
import com.yoj.model.entity.Problem;
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
