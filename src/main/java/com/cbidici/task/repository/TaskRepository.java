package com.cbidici.task.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cbidici.task.entity.Task;

public interface TaskRepository extends JpaRepository<Task, String> {

	public List<Task> findByActive(boolean active);

}
