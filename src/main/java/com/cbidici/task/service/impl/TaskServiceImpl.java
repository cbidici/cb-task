package com.cbidici.task.service.impl;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.scheduling.support.CronTrigger;
import org.springframework.stereotype.Service;

import com.cbidici.task.entity.Task;
import com.cbidici.task.entity.TaskExecution;
import com.cbidici.task.repository.TaskExecutionRepository;
import com.cbidici.task.repository.TaskRepository;
import com.cbidici.task.service.TaskExecutor;
import com.cbidici.task.service.TaskService;

@Service
public class TaskServiceImpl implements TaskService {
	
	private static Logger LOG = LoggerFactory.getLogger(TaskServiceImpl.class);

	@Autowired
	private ThreadPoolTaskScheduler taskScheduler;

	@Autowired
	private TaskRepository taskRepository;
	
	@Autowired
	private TaskExecutionRepository taskExecutionRepository;
	
	@Autowired
	private ApplicationContext applicationContext;
	
	@Override
	public List<Task> getActiveTasks() {
		return taskRepository.findByActive(true);
	}

	@PostConstruct
	public void postConstruct() {
		List<Task> activeTasks = this.getActiveTasks();
		LOG.debug(String.format("%s active task(s) found.", activeTasks.size()));
		for (Task task : activeTasks) {
			TaskExecutor taskExecutor = applicationContext.getBean(TaskBeanExecutor.class);
			taskExecutor.setTask(task);
			taskScheduler.schedule(taskExecutor, new CronTrigger(task.getCron()));
			LOG.debug(String.format("%s scheduled with bean %s.", task.getName(), task.getBeanName()));
		}
	}

	@PreDestroy
	public void preDestroy() {
		taskScheduler.getScheduledThreadPoolExecutor().shutdownNow();
	}

	@Override
	public TaskExecution save(TaskExecution execution) {
		return taskExecutionRepository.save(execution);
	}

}
