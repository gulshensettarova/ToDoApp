package com.company.toDoApp.model.dao.repository;

import com.company.toDoApp.model.dao.entity.Project;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProjectRepository extends JpaRepository<Project,Integer> {
}
