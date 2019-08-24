package com.yoj.web.service;

import com.yoj.web.bean.Solution;
import com.yoj.web.mapper.SolutionMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SolutionService {
    @Autowired
    public SolutionMapper solutionMapper;

    public int insertSolution(Solution solution) {
        return solutionMapper.insetSolution(solution);
    }
}
