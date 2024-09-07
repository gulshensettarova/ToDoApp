package com.company.toDoApp.dao.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.sql.Timestamp;
import java.util.Date;

@Getter
@Setter
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "task")
public class Task {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String title;
    private String description;
    private int priority;
    private Date deadline;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="task_project_id",referencedColumnName = "id")
    private Project project;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="task_employee_id",referencedColumnName = "id")
    private Employee employee;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="task_status_id",referencedColumnName = "id")
    private TaskStatus status;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="task_category_id", referencedColumnName = "id")
    private Category category;
    @CreationTimestamp
    @Column(name="created_at")
    private Timestamp createdAt;
    @UpdateTimestamp
    @Column(name = "updated_at")
    private Timestamp updatedAt;
    @Column(name = "is_active")
    private boolean isAcive;
}
