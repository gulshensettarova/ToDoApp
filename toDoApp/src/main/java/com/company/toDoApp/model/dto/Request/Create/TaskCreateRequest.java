package com.company.toDoApp.model.dto.Request.Create;

import com.company.toDoApp.model.enums.task.TaskStatusEnum;
import com.company.toDoApp.model.dao.entity.Project;
import lombok.Data;

import java.util.Date;
@Data
public class TaskCreateRequest {
    private String title;
    private String description;
    private int priority;
    private Date deadline;
    private Project project;
    private int employeeId;
    private TaskStatusEnum status;
    private int categoryId;
    private boolean isActive;
}
