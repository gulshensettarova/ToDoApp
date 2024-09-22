package com.company.toDoApp.model.dao.repository;

import com.company.toDoApp.model.dao.entity.Employee;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EmployeeRepository  extends JpaRepository<Employee, Integer> {
}
