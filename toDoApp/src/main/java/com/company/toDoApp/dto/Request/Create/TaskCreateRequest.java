package com.company.toDoApp.dto.Request.Create;

import lombok.Data;

import java.util.Date;
@Data
public class TaskCreateRequest {
    private String title;
    private String description;
    private int priority;
    private Date deadline;
    private int projectId;
    private int employeeId;
    private int statusId;
    private int categoryId;
    private boolean isActive;
}
