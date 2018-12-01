package com.cbidici.task.entity;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.ForeignKey;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.cbidici.task.enm.TaskExecutionStatusEnm;

@Entity
@Table(name = "cb_task_execution")
public class TaskExecution extends BaseEntity {

	@ManyToOne
	@JoinColumn(name = "task_fk", foreignKey = @ForeignKey(name = "cb_task_execution_cb_task_fk"), nullable = false)
	private Task task;

	@Enumerated(EnumType.STRING)
	private TaskExecutionStatusEnm status;

	@Column(name = "execution_start_time")
	private Timestamp executionStartTime;

	@Column(name = "execution_end_time")
	private Timestamp executionEndTime;

	private boolean success;

	@Column(columnDefinition = "text")
	private String result;

	public Task getTask() {
		return task;
	}

	public void setTask(Task task) {
		this.task = task;
	}

	public TaskExecutionStatusEnm getStatus() {
		return status;
	}

	public void setStatus(TaskExecutionStatusEnm status) {
		this.status = status;
	}

	public Timestamp getExecutionStartTime() {
		return executionStartTime;
	}

	public void setExecutionStartTime(Timestamp executionStartTime) {
		this.executionStartTime = executionStartTime;
	}

	public Timestamp getExecutionEndTime() {
		return executionEndTime;
	}

	public void setExecutionEndTime(Timestamp executionEndTime) {
		this.executionEndTime = executionEndTime;
	}

	public boolean isSuccess() {
		return success;
	}

	public void setSuccess(boolean success) {
		this.success = success;
	}

	public String getResult() {
		return result;
	}

	public void setResult(String result) {
		this.result = result;
	}

}
