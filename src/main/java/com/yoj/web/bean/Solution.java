package com.yoj.web.bean;

import com.yoj.judge.bean.TestResult;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Date;
import java.util.List;

@Getter
@Setter
@ToString
public class Solution {
	private Integer solutionId;

	private Integer problemId;

	private Integer userId;
//	Languages
	private Integer language;

	private String languageStr;

	private String code;
//	Results
	private Integer result;

	private String resultStr;
    //ms
	private Integer runtime;
	//memoryInfo = this.memory / 10 + "KB"
	private Integer memory;
	
	private String errorMessage;
	
	private User user;
	
	private List<TestResult> testResults;
	
	private Date submitTime;
	
}
