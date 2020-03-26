package com.yoj.service;

import com.yoj.mapper.ContestMapper;
import com.yoj.mapper.ContestProblemMapper;
import com.yoj.model.entity.Contest;
import com.yoj.model.entity.ContestProblem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ContestService {
    @Autowired
    private ContestMapper contestMapper;
    @Autowired
    private ContestProblemMapper contestProblemMapper;

    @Transactional
    public Boolean insert(Contest contest) {
        if (contestMapper.insert(contest) <= 0) {
            return false;
        }
        List<ContestProblem> contestProblemList = contest.getContestProblemList();
        for (ContestProblem contestProblem : contestProblemList) {
            contestProblem.setContestId(contest.getContestId());
        }
        contestProblemMapper.batchInsert(contestProblemList);
        return true;
    }

    public Contest getById(Integer contestId) {
        return contestMapper.getById(contestId);
    }

    public List<Contest> getList(Contest contest) {
        return contestMapper.getList(contest);
    }

    public Boolean updateById(Contest contest) {
        return contestMapper.updateById(contest) > 0;
    }

    /**
     *  get from two table
     * @param contestId
     * @return
     */
    public Contest getFromContestProblem(Integer contestId,Integer problemId) {
        return contestMapper.getFromContestProblem(contestId,problemId);
    }
}
