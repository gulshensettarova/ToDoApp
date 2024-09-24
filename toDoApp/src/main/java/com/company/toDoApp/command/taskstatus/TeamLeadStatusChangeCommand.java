package com.company.toDoApp.command.taskstatus;

import com.company.toDoApp.model.dao.entity.Task;
import com.company.toDoApp.model.dao.entity.TaskStatus;
import com.company.toDoApp.model.enums.task.TaskStatusEnum;
import com.company.toDoApp.service.v1.Impl.TaskStatusService;

public class TeamLeadStatusChangeCommand extends PersonStatusChange{

    public TeamLeadStatusChangeCommand(TaskStatusEnum newStatus, TaskStatusService taskStatusService) {
        super(newStatus,taskStatusService);
    }

    @Override
    public void execute(Task task) {
        if (newStatus == TaskStatusEnum.CANCELLED ||
                newStatus == TaskStatusEnum.COMPLETED ||
                newStatus == TaskStatusEnum.NOT_COMPLETED) {
            TaskStatus statusEntity = taskStatusService.findByEnum(newStatus);
            this.previousStatus = statusEntity;
            task.setStatus(statusEntity);
        }
        else {
            throw new RuntimeException("Komanda rəhbəri yalnız xüsusi statusları dəyişə bilər.");
        }

    }

}
