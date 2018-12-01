package com.cbidici.task.service.impl;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.LocalTime;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import com.cbidici.task.enm.TaskExecutionStatusEnm;
import com.cbidici.task.entity.TaskExecution;
import com.cbidici.task.exception.InvalidTaskBeanException;
import com.cbidici.task.service.TaskBeanIntf;
import com.cbidici.task.service.TaskExecutor;
import com.cbidici.task.service.TaskService;
import com.cbidici.task.util.ExceptionUtil;

@Service
@Scope(value = "prototype")
public class TaskBeanExecutor extends TaskExecutor {

	private static Logger LOG = LoggerFactory.getLogger(TaskBeanExecutor.class);

	@Autowired
	private TaskService taskService;

	@Autowired
	private ApplicationContext applicationContext;

	public TaskBeanExecutor() {
		super(null);
	}

	@Override
	public void run() {
		Object objectTaskBean = applicationContext.getBean(this.getTask().getBeanName());
		if (!TaskBeanIntf.class.isAssignableFrom(objectTaskBean.getClass())) {
			throw new InvalidTaskBeanException(this.getTask().getBeanName() + "not exists or does not implmenet TaskBeanIntf.");
		}

		TaskBeanIntf taskBean = (TaskBeanIntf) objectTaskBean;
		TaskExecution execution = null;
		try {
			// TODO change this local time to utc time
			LOG.debug(this.getTask().getName() + " task started at " + LocalTime.now() + " with thread "
					+ Thread.currentThread().getName() + ".");
			execution = initExecution();
			String message = taskBean.execute();
			finalizeExecution(execution, true, message);
			// TODO change this local time to utc time
			LOG.debug(this.getTask().getName() + " task ended at " + LocalTime.now() + " with thread "
					+ Thread.currentThread().getName() + ".");
		} catch (Exception e) {
			finalizeExecution(execution, false, ExceptionUtil.getStackTrace(e));
			LOG.error(this.getTask().getName() + " task ended at " + LocalTime.now() + " with thread "
					+ Thread.currentThread().getName() + ".", e);
		}
	}

	private TaskExecution initExecution() {
		TaskExecution execution = new TaskExecution();
		// TODO change this local time to utc time
		execution.setExecutionStartTime(Timestamp.valueOf(LocalDateTime.now()));
		execution.setTask(getTask());
		execution.setStatus(TaskExecutionStatusEnm.PROCESSING);
		return taskService.save(execution);
	}

	private void finalizeExecution(TaskExecution execution, boolean success, String message) {
		// TODO change this local time to utc time
		execution.setExecutionEndTime(Timestamp.valueOf(LocalDateTime.now()));
		execution.setSuccess(success);
		execution.setResult(message);
		execution.setStatus(TaskExecutionStatusEnm.COMPLETED);
		taskService.save(execution);
	}

}
