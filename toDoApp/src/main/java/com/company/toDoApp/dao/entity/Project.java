package com.company.toDoApp.dao.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Data
@Entity
@Table(name="project")
public class Project {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(name="project_name")
    private String projectName;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="team_id",referencedColumnName = "id")
    private Team team;
    private boolean isActive;
    @OneToMany(mappedBy = "project",cascade = CascadeType.ALL)
    private List<Task> tasks;
}
