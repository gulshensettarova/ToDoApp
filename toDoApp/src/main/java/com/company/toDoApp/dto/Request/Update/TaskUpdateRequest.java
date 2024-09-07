package com.company.toDoApp.dto.Request.Update;

import lombok.Data;

import java.util.Date;
@Data
public class TaskUpdateRequest
{
    private int id;
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
