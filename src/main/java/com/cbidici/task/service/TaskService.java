package com.cbidici.task.service;

import java.util.List;

import com.cbidici.task.entity.Task;
import com.cbidici.task.entity.TaskExecution;

public interface TaskService {

	public List<Task> getActiveTasks();

	public TaskExecution save(TaskExecution execution);

}
