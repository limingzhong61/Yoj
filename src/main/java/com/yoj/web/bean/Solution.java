package com.yoj.web.bean;

import java.util.Date;
import java.util.List;

import com.yoj.judge.bean.TestResult;

//@Getter
//@Setter
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
	
	
	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Date getSubmitTime() {
		return submitTime;
	}

	public void setSubmitTime(Date submitTime) {
		this.submitTime = submitTime;
	}

	public String getErrorMessage() {
		return errorMessage;
	}

	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}

	public Integer getRuntime() {
		return runtime;
	}

	public void setRuntime(Integer runtime) {
		this.runtime = runtime;
	}

	public Integer getMemory() {
		return memory;
	}

	public void setMemory(Integer memory) {
		this.memory = memory;
	}

	public Integer getSolutionId() {
		return solutionId;
	}

	public void setSolutionId(Integer solutionId) {
		this.solutionId = solutionId;
	}

	public Integer getProblemId() {
		return problemId;
	}

	public void setProblemId(Integer problemId) {
		this.problemId = problemId;
	}

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public String getLanguage() {
		return language;
	}

	public void setLanguage(String language) {
		this.language = language;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getResult() {
		return result;
	}

	public void setResult(String result) {
		this.result = result;
	}

	
	public List<TestResult> getTestResults() {
		return testResults;
	}

	public void setTestResults(List<TestResult> testResults) {
		this.testResults = testResults;
	}

}
