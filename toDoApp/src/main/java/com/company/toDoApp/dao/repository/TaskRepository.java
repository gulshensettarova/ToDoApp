package com.company.toDoApp.dao.repository;

import com.company.toDoApp.dao.entity.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TaskRepository extends JpaRepository<Task,Integer> {

}
