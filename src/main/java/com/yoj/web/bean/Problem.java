package com.yoj.web.bean;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Problem {
    public Integer problemId;

    public Integer userId;

    public String title;

    public String description;

    public Integer memoryLimit;

    public Integer timeLimit;

    public Integer accepted;

    public Integer submissions;

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

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Integer getMemoryLimit() {
		return memoryLimit;
	}

	public void setMemoryLimit(Integer memoryLimit) {
		this.memoryLimit = memoryLimit;
	}

	public Integer getTimeLimit() {
		return timeLimit;
	}

	public void setTimeLimit(Integer timeLimit) {
		this.timeLimit = timeLimit;
	}

	public Integer getAccepted() {
		return accepted;
	}

	public void setAccepted(Integer accepted) {
		this.accepted = accepted;
	}

	public Integer getSubmissions() {
		return submissions;
	}

	public void setSubmissions(Integer submissions) {
		this.submissions = submissions;
	}

    
}
