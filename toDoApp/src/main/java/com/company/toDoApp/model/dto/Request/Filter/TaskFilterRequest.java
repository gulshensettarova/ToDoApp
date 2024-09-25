package com.company.toDoApp.model.dto.Request.Filter;

import lombok.Data;

import java.util.Date;
@Data
public class TaskFilterRequest {
    private String title;
    private Integer priority;
    private Date deadline;
    private Integer projectId;
    private Integer employeeId;
    private Integer statusId;
    private Integer categoryId;
}
