package com.company.toDoApp.dao.repository;

import com.company.toDoApp.dao.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Integer> {
    //Qeyd : Optional null deyerlerden qacmaq ucun istifade olunan bir sinifdir.
    //Bu metoddan qayidan netice null olarsa NullPointerException atacaq ve biz orElseThrow istifade etmekle qayidan xetani idare ede bilecik
    Optional<User> findByUserEmail(String email);
}
