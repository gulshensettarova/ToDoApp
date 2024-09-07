package com.company.toDoApp.dao.entity;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;

import java.sql.Timestamp;

@Entity
@Data
@Table(name="sessions")
public class Sessions {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="user_id",referencedColumnName = "id")
    private User user;
    @Column(name="session_token")
    private String sessionToken;
    @CreationTimestamp
    @Column(name="created_at")
    private Timestamp createdAt;
    @Column(name="expires_at")
    private Timestamp expiresAt;
}
