package com.cbidici.task.enm;

public enum TaskExecutionStatusEnm {
	PROCESSING("PROCESSING"),
	COMPLETED("COMPLETED");
	
	private String code;
	
	private TaskExecutionStatusEnm(String code){
		this.code = code;
	}

	public String getCode() {
		return code;
	}
}
