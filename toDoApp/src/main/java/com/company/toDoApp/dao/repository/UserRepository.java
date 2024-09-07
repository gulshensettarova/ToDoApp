package com.company.toDoApp.dao.repository;

import com.company.toDoApp.dao.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Integer> {
}
