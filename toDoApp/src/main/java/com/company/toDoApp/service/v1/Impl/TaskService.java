package com.company.toDoApp.service.v1.Impl;

import com.company.toDoApp.model.enums.task.TaskStatusEnum;
import com.company.toDoApp.model.dao.entity.Task;
import com.company.toDoApp.model.dao.repository.TaskRepository;
import com.company.toDoApp.mapper.TaskMapper;
import com.company.toDoApp.service.v1.Inter.TaskInterface;

public class TaskService implements TaskInterface {
    private TaskMapper taskMapper;
    private TaskRepository taskRepository;
    public TaskService(TaskMapper taskMapper, TaskRepository taskRepository) {
        this.taskMapper = taskMapper;
        this.taskRepository = taskRepository;
    }

    @Override
    public Task newTask() {
        return null;
    }

    @Override
    public Boolean deleteTask() {
        return null;
    }

    @Override
    public Boolean updateTask() {
        return null;
    }

    @Override
    public Boolean changeStatus() {
        return null;
    }

    @Override
    public TaskStatusEnum getStatus() {
        return null;
    }
}
