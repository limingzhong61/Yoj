package com.yoj.judge.bean;

/**
 * 
 * @author nicolas
 *	one test result 
 */
public class TestResult {
	private Integer timeUsed;
	private Integer memoryUsed;
	private String result;

	public Integer getTimeUsed() {
		return timeUsed;
	}

	public void setTimeUsed(Integer timeUsed) {
		this.timeUsed = timeUsed;
	}

	public Integer getMemoryUsed() {
		return memoryUsed;
	}

	public void setMemoryUsed(Integer memoryUsed) {
		this.memoryUsed = memoryUsed;
	}

	public String getResult() {
		return result;
	}

	public void setResult(String result) {
		this.result = result;
	}

	@Override
	public String toString() {
		return "StdOut [timeUsed=" + timeUsed + ", memoryUsed=" + memoryUsed + ", result=" + result + "]";
	}

	
}
