package com.company.toDoApp.model.dao.entity;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;

import java.sql.Timestamp;

@Entity
@Data
@Table(name="password_resets")
public class PasswordReset {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="user_id",referencedColumnName = "id")
    private User user;
    @Column(name="reset_token")
    private String resetToken;
    @CreationTimestamp
    @Column(name="created_at")
    private Timestamp createdAt;
    @Column(name="expires_at")
    private Timestamp expiresAt;
}
