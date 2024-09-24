package com.company.toDoApp.command.taskstatus;

import com.company.toDoApp.model.dao.entity.Task;

public interface TaskStatusChangeCommand {
    void execute(Task task);

    void undo(Task task);
}
