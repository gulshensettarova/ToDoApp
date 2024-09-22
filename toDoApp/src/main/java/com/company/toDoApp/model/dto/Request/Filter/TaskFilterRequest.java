package com.company.toDoApp.model.dto.Request.Filter;

import lombok.Data;

import java.util.Date;
@Data
public class TaskFilterRequest {
    private String title;
    private int priority;
    private Date deadline;
    private int projectId;
    private int employeeId;
    private int statusId;
    private int categoryId;
}
