package com.company.toDoApp.service.v1.Inter;


import com.company.toDoApp.model.enums.task.TaskStatusEnum;
import com.company.toDoApp.model.dao.entity.Task;

public interface TaskInterface {
    Task newTask();
    Boolean deleteTask();
    Boolean updateTask();
    Boolean changeStatus();
    TaskStatusEnum getStatus();

}
