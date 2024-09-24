package com.company.toDoApp.service.v1.impl.task;

import com.company.toDoApp.model.dao.entity.TaskStatus;
import com.company.toDoApp.model.dao.repository.TaskStatusRepository;
import com.company.toDoApp.model.enums.task.TaskStatusEnum;
import com.company.toDoApp.service.v1.inter.task.TaskStatusInterface;

public class TaskStatusService implements TaskStatusInterface {
    private TaskStatusRepository taskStatusRepository;

    @Override
    public TaskStatus findByEnum(TaskStatusEnum statusEnum) {
        return taskStatusRepository.findByName(statusEnum.name())
                .orElseThrow(() -> new RuntimeException("Status tapılmadı: " + statusEnum));
    }
}
