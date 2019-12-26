package com.example.task.models;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class TaskExecutionResp {

	private int executionCount;
	
	public TaskExecutionResp() {
	}

	@JsonCreator
	public TaskExecutionResp(@JsonProperty("executionsCount") int executionCount) {
		this.executionCount = executionCount;
	}

	public int getExecutionsCount() {
		return executionCount;
	}

	public void setExecutionsCount(int executionsCount) {
		this.executionCount = executionsCount;
	}

}
