package com.cbidici.task;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.mockito.hamcrest.MockitoHamcrest.argThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.hamcrest.Matchers.instanceOf;

import java.util.ArrayList;
import java.util.List;


import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import com.cbidici.task.entity.Task;
import com.cbidici.task.entity.TaskExecution;
import com.cbidici.task.repository.TaskRepository;
import com.cbidici.task.service.TaskBeanIntf;
import com.cbidici.task.service.impl.TaskBeanExecutor;
import com.cbidici.task.service.impl.TaskServiceImpl;

@RunWith(SpringRunner.class)
public class CbTaskApplicationTests {

	@InjectMocks
	private TaskServiceImpl taskServiceInjected;
	
	@InjectMocks
	private TaskBeanExecutor executor;
	
	@Mock
	private TaskServiceImpl taskService;
	
	@Mock
	private TaskRepository taskRepository;

	@Mock
	private TaskBeanExecutor taskBeanExecuter;

	@Mock
	private ApplicationContext applicationContext;
	
	@Mock
	private ThreadPoolTaskScheduler taskScheduler;

	TaskBeanIntf testTaskBean = new TaskBeanIntf() {

		@Override
		public String execute() {
			return "EXECUTED";
		}
	};

	@Before
	public void before() {
		List<Task> activeTaskList = new ArrayList<>();
		Task task = new Task();
		task.setActive(true);
		task.setBeanName("testTaskBean");
		task.setCron("* * * * * *");
		task.setName("Test Task Bean");
		activeTaskList.add(task);
		
		taskScheduler.setPoolSize(1);
		taskScheduler.initialize();
		
		executor.setTask(task);
		
		when(taskRepository.findByActive(true)).thenReturn(activeTaskList);
		when(applicationContext.getBean("testTaskBean")).thenReturn(testTaskBean);
		when(applicationContext.getBean(TaskBeanExecutor.class)).thenReturn(taskBeanExecuter);
	}

	@Test
	public void shouldFindActiveTasks() {
		List<Task> actualTasks = taskServiceInjected.getActiveTasks();
		verify(taskRepository).findByActive(true);
		assertThat(actualTasks, hasSize(1));
	}

	@Test
	public void shouldExecuteTask() {
		taskServiceInjected.postConstruct();
		verify(applicationContext).getBean(TaskBeanExecutor.class);
	}
	
	@Test
	public void shouldInitExecution() {
		TaskExecution execution = new TaskExecution();
		when(taskService.save(argThat(instanceOf(TaskExecution.class)))).thenReturn(execution);
		executor.run();
		verify(taskService).save(execution);
	}
}
