package com.company.toDoApp.service.v1.Inter;


import com.company.toDoApp.model.dto.Request.Create.TaskCreateRequest;
import com.company.toDoApp.model.enums.task.TaskStatusEnum;
import com.company.toDoApp.model.dao.entity.Task;

public interface TaskInterface {
    Task newTask(TaskCreateRequest request);
    Boolean deleteTask(int id);
    Boolean updateTask();
    Boolean changeStatus();
    TaskStatusEnum getStatus();

}
