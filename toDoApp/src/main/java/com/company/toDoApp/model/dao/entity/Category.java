package com.company.toDoApp.model.dao.entity;

import jakarta.persistence.*;
import com.company.toDoApp.model.dao.entity.Task;
import lombok.Data;

import java.util.List;

@Entity
@Data
@Table(name="category")
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(name = "category_name")
    private String categoryName;
    @Column(name = "is_active")
    private boolean isAcive;
    @OneToMany(mappedBy = "category",cascade = CascadeType.ALL)
    private List<Task> tasks;
}