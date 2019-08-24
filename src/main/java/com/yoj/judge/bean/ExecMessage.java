package com.yoj.judge.bean;

public class ExecMessage {

	private String error;

	private String stdout;
	
	public	ExecMessage() {
		
	}
	

	public ExecMessage(String error, String stdout) {
		super();
		this.error = error;
		this.stdout = stdout;
	}



	public String getError() {
		return error;
	}

	public void setError(String error) {
		this.error = error;
	}

	public String getStdout() {
		return stdout;
	}

	public void setStdout(String stdout) {
		this.stdout = stdout;
	}
	
	
}