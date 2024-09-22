package com.company.toDoApp.model.dao.entity;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.sql.Timestamp;
import java.util.List;

@Data
@Entity
@Table(name="team")
public class Team {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(name = "team_name")
    private String teamName;
    @Column(name = "team_leader_id")
    private int teamLeaderId;
    @CreationTimestamp
    @Column(name="created_at")
    private Timestamp createdAt;
    @UpdateTimestamp
    @Column(name = "updated_at")
    private Timestamp updatedAt;
    @Column(name = "is_active")
    private boolean isAcive;
    @OneToMany(mappedBy = "team",cascade = CascadeType.ALL)
    private List<Project> projects;
    @OneToMany(mappedBy = "team",cascade = CascadeType.ALL)
    private List<Employee> employees;
}
