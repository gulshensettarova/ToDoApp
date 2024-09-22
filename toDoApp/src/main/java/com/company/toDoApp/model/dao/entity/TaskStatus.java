package com.company.toDoApp.model.dao.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Entity
@Data
@Table(name="task_status")
public class TaskStatus {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String taskStatus;
    @OneToMany(mappedBy = "status",cascade = CascadeType.ALL)
    private List<Task> tasks;
}