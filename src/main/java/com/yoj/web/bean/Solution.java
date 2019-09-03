package com.yoj.web.bean;

import com.yoj.judge.bean.TestResult;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.util.List;

@Getter
@Setter
public class Solution {
	private Integer solutionId;

	private Integer problemId;

	private Integer userId;

	private String language;

	private String code;

	private String result;
    
	private Integer runtime;
    
	private Integer memory;
	
	private String errorMessage;
	
	private User user;
	
	private List<TestResult> testResults;
	
	private Date submitTime;
	
}
