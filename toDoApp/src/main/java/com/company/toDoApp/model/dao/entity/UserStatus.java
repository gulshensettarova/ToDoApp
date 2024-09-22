package com.company.toDoApp.model.dao.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Entity
@Data
@Table(name="user_status")
public class UserStatus {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(name="user_status")
    private String userStatus;
    @OneToMany(mappedBy = "userStatus", cascade = CascadeType.ALL)
    private List<User> users;
}
