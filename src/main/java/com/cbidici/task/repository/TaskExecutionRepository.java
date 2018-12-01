package com.cbidici.task.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cbidici.task.entity.TaskExecution;

public interface TaskExecutionRepository extends JpaRepository<TaskExecution, String> {

}
