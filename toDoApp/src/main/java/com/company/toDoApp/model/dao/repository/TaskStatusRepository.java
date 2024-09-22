package com.company.toDoApp.model.dao.repository;

import com.company.toDoApp.model.dao.entity.TaskStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TaskStatusRepository extends JpaRepository<TaskStatus,Integer> {
    Optional<TaskStatus> findByName(String name);
}
