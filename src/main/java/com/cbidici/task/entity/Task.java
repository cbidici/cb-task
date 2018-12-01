package com.cbidici.task.entity;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name="cb_task")
public class Task extends BaseEntity {
	
	private String name;
	
	private boolean active;
	
	@Column(name="bean_name")
	private String beanName;

	private String cron;
	
	@OneToMany(mappedBy="task", fetch=FetchType.LAZY)
	private List<TaskExecution> executions;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public boolean isActive() {
		return active;
	}

	public void setActive(boolean active) {
		this.active = active;
	}

	public String getBeanName() {
		return beanName;
	}

	public void setBeanName(String beanName) {
		this.beanName = beanName;
	}

	public String getCron() {
		return cron;
	}

	public void setCron(String cron) {
		this.cron = cron;
	}

	public List<TaskExecution> getExecutions() {
		return executions;
	}

	public void setExecutions(List<TaskExecution> executions) {
		this.executions = executions;
	}

}
