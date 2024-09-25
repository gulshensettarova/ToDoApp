package com.company.toDoApp.service.v1.inter.task;


import com.company.toDoApp.model.dto.Request.Create.TaskCreateRequest;
import com.company.toDoApp.model.dto.Request.Filter.TaskFilterRequest;
import com.company.toDoApp.model.dto.Response.Base.SuccessResponse;
import com.company.toDoApp.model.dto.Response.TaskResponse;
import com.company.toDoApp.model.enums.task.TaskStatusEnum;
import com.company.toDoApp.model.dao.entity.Task;

import java.util.List;

public interface TaskInterface {
    SuccessResponse<Object> newTask(TaskCreateRequest request);
    Boolean deleteTask(int id);
    Boolean updateTask();
    Boolean changeStatus(int taskId,TaskStatusEnum newstatus);
    TaskResponse getTaskById(int id);
    List<TaskResponse> getAllTasks();
    List<TaskResponse> filterTasks(TaskFilterRequest request);
}
