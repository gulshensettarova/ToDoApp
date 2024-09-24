package com.company.toDoApp.service.v1.inter.task;

import com.company.toDoApp.model.dao.entity.TaskStatus;
import com.company.toDoApp.model.enums.task.TaskStatusEnum;

public interface TaskStatusInterface {
    TaskStatus findByEnum(TaskStatusEnum statusEnum);
}
