package com.yoj.web.dao;

import com.yoj.web.pojo.ContestProblem;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Mapper
public interface ContestProblemMapper {

   Integer batchInsert(List<ContestProblem> contestProblemList);
}
