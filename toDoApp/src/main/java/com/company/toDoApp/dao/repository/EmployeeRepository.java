package com.company.toDoApp.dao.repository;

import com.company.toDoApp.dao.entity.Employee;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EmployeeRepository  extends JpaRepository<Employee, Integer> {
}
