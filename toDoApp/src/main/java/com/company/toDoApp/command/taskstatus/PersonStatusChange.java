package com.company.toDoApp.command.taskstatus;

import com.company.toDoApp.model.dao.entity.Task;
import com.company.toDoApp.model.dao.entity.TaskStatus;
import com.company.toDoApp.model.enums.task.TaskStatusEnum;
import com.company.toDoApp.service.v1.impl.task.TaskStatusService;

public abstract class PersonStatusChange implements TaskStatusChangeCommand{
    protected final TaskStatusEnum newStatus;
    protected final TaskStatusService taskStatusService;
    protected TaskStatus previousStatus;

    public PersonStatusChange(TaskStatusEnum newStatus, TaskStatusService taskStatusService) {
        this.newStatus = newStatus;
        this.taskStatusService = taskStatusService;
    }
    @Override
    public void undo(Task task) {
        if (previousStatus != null) {
            task.setStatus(previousStatus);
        }
    }
}
