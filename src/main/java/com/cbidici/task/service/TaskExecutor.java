package com.cbidici.task.service;

import com.cbidici.task.entity.Task;

public abstract class TaskExecutor implements Runnable {
	
	private Task task;
	
	public TaskExecutor (Task task) {
		this.task = task;
	}

	public Task getTask() {
		return task;
	}
	
	public void setTask(Task task) {
		this.task = task;
	}
	
}
