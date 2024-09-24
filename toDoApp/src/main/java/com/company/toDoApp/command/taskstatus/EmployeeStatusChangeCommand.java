package com.company.toDoApp.command.taskstatus;

import com.company.toDoApp.model.dao.entity.Task;
import com.company.toDoApp.model.dao.entity.TaskStatus;
import com.company.toDoApp.model.enums.task.TaskStatusEnum;
import com.company.toDoApp.service.v1.impl.task.TaskStatusService;

public class EmployeeStatusChangeCommand  extends PersonStatusChange{
    public EmployeeStatusChangeCommand(TaskStatusEnum newStatus, TaskStatusService taskStatusService) {
        super(newStatus,taskStatusService);
    }

    @Override
    public void execute(Task task) {
        if (newStatus == TaskStatusEnum.IN_PROGRESS ||
                newStatus == TaskStatusEnum.ON_HOLD) {
            TaskStatus statusEntity = taskStatusService.findByEnum(newStatus);
            this.previousStatus = statusEntity;
            task.setStatus(statusEntity);
        } else {
            throw new RuntimeException("İşçi yalnız icra ilə bağlı statusları dəyişə bilər.");
        }
    }

}
