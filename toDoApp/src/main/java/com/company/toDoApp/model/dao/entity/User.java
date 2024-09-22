package com.company.toDoApp.model.dao.entity;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.sql.Timestamp;
import java.util.List;

@Entity
@Data
@Table(name="userr")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(name = "user_email")
    private String userEmail;
    @Column(name = "user_password_hash")
    private String userPasswordHash;
    @Column(name = "is_email_confirmed")
    private boolean isConfirmed;
    @CreationTimestamp
    @Column(name="created_at")
    private Timestamp createdAt;
    @UpdateTimestamp
    @Column(name = "updated_at")
    private Timestamp updatedAt;
    @Column(name = "is_active")
    private boolean isActive;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_status_id", referencedColumnName = "id")
    private UserStatus userStatus;
    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL)
    private Employee employee;
    @OneToMany(mappedBy = "user",cascade = CascadeType.ALL)
    private List<PasswordReset> passwordResetList;
    @OneToMany(mappedBy = "user",cascade = CascadeType.ALL)
    private List<Sessions> sessionsList;
}
