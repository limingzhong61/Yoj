package com.yoj.web.pojo;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Date;
import java.util.List;

@Setter
@Getter
@ToString
public class Contest {
    private Integer contestId;
    private Integer userId;
    private String title;
    private Date startTime;
    private Date endTime;
    private String description;

    // one contest has lots of problem
    private List<ContestProblem> contestProblemList;
    //    private String private;
//    private String password;
//    private String defunct;
}
