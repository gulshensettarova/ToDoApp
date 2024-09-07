package com.company.toDoApp.dao.entity;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

@Entity
@Data
@Table(name = "employee")
public class Employee {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "employee_name")
    private String employeeName;

    private String employeeSurname;

    private Date employeeBirthdate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "team_id", referencedColumnName = "id")
    private Team team;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "profession_id", referencedColumnName = "id")
    private Profession profession;

    @OneToOne
    @JoinColumn(name = "employee_user_id")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "employee_user_status_id", referencedColumnName = "id")
    private UserStatus userStatus;

    @CreationTimestamp
    @Column(name = "created_at")
    private Timestamp createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at")
    private Timestamp updatedAt;

    @Column(name = "is_active")
    private boolean isActive;

    @OneToMany(mappedBy = "employee", cascade = CascadeType.ALL)
    private List<Task> tasks;
}
