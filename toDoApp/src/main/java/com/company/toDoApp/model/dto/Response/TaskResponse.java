package com.company.toDoApp.model.dto.Response;

import com.company.toDoApp.model.dao.entity.Employee;
import com.company.toDoApp.model.dao.entity.Project;
import com.company.toDoApp.model.dao.entity.TaskStatus;
import lombok.Builder;
import lombok.Data;

import java.sql.Timestamp;
import java.util.Date;

@Data
@Builder
public class TaskResponse {
    private int id;
    private String title;
    private String description;
    private int priority;
    private Date deadline;
    private Project project;
    private Employee employee;
    private TaskStatus status;
    private String category;
    private Timestamp createdAt;
    private Timestamp updatedAt;
    private boolean isAcive;
}
